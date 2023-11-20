package hub

import (
	"bytes"
	"encoding/json"
	"net/http"
	"strings"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

var allowedOrigins = [1]string{"http://localhost:4200"}

var upgrader = websocket.Upgrader{
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
	Subprotocols:    []string{"webinterface"},
	CheckOrigin: func(r *http.Request) bool {
		logrus.Info("IN_REQ: ", r)
		for i := 0; i < len(allowedOrigins); i++ {
			if r.Header["Origin"][0] == allowedOrigins[i] {
				return true
			}
		}
		return false
	},
}

// Client is a middleman between the websocket connection and the hub.
type Client struct {
	hub *Hub

	// The websocket connection.
	conn *websocket.Conn

	// The port of the client connection
	ip string
	// The port of the client connection
	port string

	// Buffered channel of outbound messages.
	Send chan messages.Message

	Id string

	seq int64
}

// readPump pumps messages from the websocket connection to the hub.
//
// The application runs readPump in a per-connection goroutine. The application
// ensures that there is at most one reader on a connection by executing all
// reads from this goroutine.
func (client *Client) readPump() {
	defer func() {
		client.hub.unregister <- client

		msg := messages.Message{
			Seq:      client.seq,
			Event:    events.Bye,
			Content:  client.Id,
			SenderIP: client.conn.RemoteAddr().String(),
		}

		// broadcast message when a client is un-registered
		client.hub.broadcast <- msg

		// close the client connection
		client.conn.Close()
	}()

	client.conn.SetReadLimit(maxMessageSize)
	client.conn.SetReadDeadline(time.Now().Add(pongWait))
	client.conn.SetPongHandler(func(string) error { client.conn.SetReadDeadline(time.Now().Add(pongWait)); return nil })

	for {
		_, message, err := client.conn.ReadMessage()
		if err != nil {
			if websocket.IsUnexpectedCloseError(err, websocket.CloseGoingAway) {
				logrus.Printf("error: %v", err)
			}
			break
		}
		message = bytes.TrimSpace(bytes.Replace(message, newline, space, -1))

		var nucleusMessage messages.Message

		err = json.Unmarshal([]byte(message), &nucleusMessage)
		if err != nil {
			logrus.Error(err)
		}

		client.hub.broadcast <- nucleusMessage
	}
}

// writePump pumps messages from the hub to the websocket connection.
//
// A goroutine running writePump is started for each connection. The
// application ensures that there is at most one writer to a connection by
// executing all writes from this goroutine.
func (c *Client) writePump() {
	ticker := time.NewTicker(pingPeriod)

	defer func() {
		ticker.Stop()
		c.conn.Close()
	}()

	for {
		select {
		case message, ok := <-c.Send:
			c.SendMessage(message, ok)

		case <-ticker.C:
			c.conn.SetWriteDeadline(time.Now().Add(writeWait))
			if err := c.conn.WriteMessage(websocket.PingMessage, []byte{}); err != nil {
				return
			}
		}
	}
}

func (c *Client) SendMessage(message messages.Message, ok bool) {
	c.conn.SetWriteDeadline(time.Now().Add(writeWait))
	if !ok {
		// The hub closed the channel.
		c.conn.WriteMessage(websocket.CloseMessage, []byte{})
		return
	}

	w, err := c.conn.NextWriter(websocket.TextMessage)
	if err != nil {
		return
	}

	c.seq += 1

	jsonMessage, err := json.Marshal(&message)
	if err != nil {
		logrus.Error(err)
		return
	}
	logrus.Println("out: " + string(jsonMessage))

	w.Write(jsonMessage)

	if err := w.Close(); err != nil {
		return
	}
}

// serveWs handles websocket requests from the peer.
func (h *Hub) ServeWebSocket(w http.ResponseWriter, r *http.Request) {
	conn, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		logrus.Println(err)
		return
	}

	nucleusClientId := r.Header.Get("Sec-WebSocket-Protocol")
	if nucleusClientId == "" {
		w.WriteHeader(400)
		return
	}

	clientAddress := strings.Split(conn.RemoteAddr().String(), ":")

	client := &Client{
		seq:  1,
		hub:  h,
		conn: conn,
		ip:   clientAddress[0],
		port: clientAddress[1],
		Send: make(chan messages.Message, 256),
		Id:   nucleusClientId,
	}

	client.hub.register <- client

	msg := messages.Message{
		Seq: client.seq,
		Data: &messages.MessageData{
			ServerVersion:    config.Version,
			MinClientVersion: config.NucleusConf.Project6.MinClientVersion},
		Event:    events.Hello,
		Content:  client.Id,
		SenderIP: client.conn.RemoteAddr().String(),
	}

	// broadcast message when client is registered
	client.hub.broadcast <- msg

	// Allow collection of memory referenced by the caller by doing all work in
	// new goroutines.
	go client.writePump()
	go client.readPump()
}
