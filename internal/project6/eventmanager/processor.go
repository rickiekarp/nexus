package eventmanager

import (
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"git.rickiekarp.net/rickie/home/internal/project6/config"
	"github.com/sirupsen/logrus"
)

func ProcessMessage(receivedMessageBytes []byte) {
	logrus.Printf("recv: %s", receivedMessageBytes)
	nucleusMessage := convertToMessage(receivedMessageBytes)
	processEvent(*nucleusMessage)
}

func processEvent(message messages.Message) {
	switch message.Event {
	case events.Hello:
		if config.Version < message.Data.MinClientVersion {
			logrus.Println("(stub) Local version is too old. Updating...")
		}
		// TODO
	case events.ConfigChange:
		logrus.Info("checking config: ", message.Event)
		// TODO
	}
}
