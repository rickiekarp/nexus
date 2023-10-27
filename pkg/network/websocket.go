package network

import (
	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

func CloseWebSocket(connection *websocket.Conn) error {
	// Cleanly close the connection by sending a close message
	err := connection.WriteMessage(websocket.CloseMessage, websocket.FormatCloseMessage(websocket.CloseNormalClosure, ""))
	if err != nil {
		logrus.Println("write close:", err)
	}
	return err
}
