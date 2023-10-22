package api

import (
	"io/ioutil"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"github.com/sirupsen/logrus"
)

func SendMessage(w http.ResponseWriter, r *http.Request) {
	val, ok := r.Header[recipientHeader]
	if ok {
		for client := range hub.Nucleus.Clients {
			if client.Id == val[0] {
				client.SendMessage([]byte("send to: "+client.Id), true)
			}
		}
		w.WriteHeader(200)
	} else {
		logrus.Info("Missing header: " + recipientHeader + " (Origin: " + r.RemoteAddr + ")")
		w.WriteHeader(400)
	}
}

func BroadcastMessage(w http.ResponseWriter, r *http.Request) {

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}

	if len(body) == 0 {
		w.WriteHeader(400)
	}

	for client := range hub.Nucleus.Clients {
		client.SendMessage(body, true)
	}
	w.WriteHeader(200)
}
