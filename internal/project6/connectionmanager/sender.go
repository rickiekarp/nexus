package connectionmanager

import (
	"encoding/json"

	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

func SendMessage(nexusMessage *messages.Message) {
	jsonMessage, err := json.Marshal(&nexusMessage)
	if err != nil {
		logrus.Error(err)
		return
	}
	logrus.Println("OUT: " + string(jsonMessage))
	Connection.WriteMessage(websocket.TextMessage, jsonMessage)
}
