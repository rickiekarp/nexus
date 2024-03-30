package eventmanager

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"github.com/sirupsen/logrus"
)

func ProcessMessage(receivedMessageBytes []byte) {
	logrus.Printf("recv: %s", receivedMessageBytes)
	nexusMessage := convertToMessage(receivedMessageBytes)
	processEvent(*nexusMessage)
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
