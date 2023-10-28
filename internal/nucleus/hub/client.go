package hub

import (
	"bytes"
	"encoding/json"
	"net/http"
	"strings"
	"time"

	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

var upgrader = websocket.Upgrader{
	ReadBufferSize:  1024,
	WriteBufferSize: 1024,
	Subprotocols:    []string{"webinterface"},
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
	Send chan []byte

	Id string

	seq int64
}

// readPump pumps messages from the websocket connection to the hub.
//
// The application runs readPump in a per-connection goroutine. The application
// ensures that there is at most one reader on a connection by executing all
// reads from this goroutine.
func (c *Client) readPump() {
	defer func() {
		c.hub.unregister <- c
		c.conn.Close()
	}()
	c.conn.SetReadLimit(maxMessageSize)
	c.conn.SetReadDeadline(time.Now().Add(pongWait))
	c.conn.SetPongHandler(func(string) error { c.conn.SetReadDeadline(time.Now().Add(pongWait)); return nil })
	for {
		_, message, err := c.conn.ReadMessage()
		if err != nil {
			if websocket.IsUnexpectedCloseError(err, websocket.CloseGoingAway) {
				logrus.Printf("error: %v", err)
			}
			break
		}
		message = bytes.TrimSpace(bytes.Replace(message, newline, space, -1))
		c.hub.broadcast <- message
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

func (c *Client) SendMessage(message []byte, ok bool) {
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

	jsonMessage, _ := json.Marshal(&Message{
		Seq:      c.seq,
		Event:    "message",
		Content:  string(message),
		SenderIP: c.conn.RemoteAddr().String(),
	})
	logrus.Println(string(jsonMessage))
	w.Write(jsonMessage)

	// Add queued chat messages to the current websocket message.
	n := len(c.Send)
	for i := 0; i < n; i++ {
		w.Write(newline)
		w.Write(<-c.Send)
	}

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
		hub:  h,
		conn: conn,
		ip:   clientAddress[0],
		port: clientAddress[1],
		Send: make(chan []byte, 256),
		Id:   nucleusClientId,
	}

	client.hub.register <- client

	// broadcast message when client is registered
	client.hub.broadcast <- []byte("joined: " + client.Id)

	// Allow collection of memory referenced by the caller by doing all work in
	// new goroutines.
	go client.writePump()
	go client.readPump()
}
