package hub

import (
	"io"
	"net/http"

	"git.rickiekarp.net/rickie/nexusform"
	"github.com/sirupsen/logrus"
)

const recipientHeader string = "X-Recipient-Client"

func SendMessage(w http.ResponseWriter, r *http.Request) {
	val, ok := r.Header[recipientHeader]
	if ok {
		if val[0] == "broadcast" {
			BroadcastMessage(w, r)
		} else {
			// TODO: create proper message
			msg := nexusform.HubMessage{
				Seq:      999,
				Event:    nexusform.Message,
				Message:  string("TEST"),
				ClientIP: "1.2.3.4",
			}
			SendMessageToClient(msg, val[0])
		}
		w.WriteHeader(200)
	} else {
		logrus.Info("Missing header: " + recipientHeader + " (Origin: " + r.RemoteAddr + ")")
		w.WriteHeader(400)
	}
}

func SendMessageToClient(message nexusform.HubMessage, clientId string) {
	if clientId == "broadcast" {
		Nexus.broadcast <- message
	} else {
		for client := range Nexus.Clients {
			if client.Id == clientId {
				client.SendMessage(message, true)
			}
		}
	}

}

func BroadcastMessage(w http.ResponseWriter, r *http.Request) {
	body, err := io.ReadAll(r.Body)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}

	if len(body) == 0 {
		w.WriteHeader(400)
	}

	for client := range Nexus.Clients {
		msg := nexusform.HubMessage{
			Seq:      client.seq,
			Event:    nexusform.Message,
			Message:  string(body),
			ClientIP: client.conn.RemoteAddr().String(),
		}
		client.SendMessage(msg, true)
	}
	w.WriteHeader(200)
}
