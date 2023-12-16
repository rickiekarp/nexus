package eventmanager

import (
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
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
		CheckForUpdate(message)
	case events.PreferencesChanged:
		logrus.Info("received preferences_changed: ", message.Data.MinClientVersion)
		CheckForUpdate(message)
	}
}
