package eventmanager

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"git.rickiekarp.net/rickie/home/internal/project6/connectionmanager"
	"git.rickiekarp.net/rickie/home/pkg/nexuslib/account"
	"git.rickiekarp.net/rickie/home/pkg/nexuslib/messageconverter"
	"github.com/sirupsen/logrus"
)

func ProcessMessage(receivedMessageBytes []byte) {
	logrus.Printf("IN: %s", receivedMessageBytes)
	nexusMessage := messageconverter.ConvertToMessage(receivedMessageBytes)
	processEvent(*nexusMessage)
}

func processEvent(message messages.Message) {
	switch message.Event {
	case events.Hello:
		if account.Profile == nil {
			account.Profile = &account.Account{
				Id: message.Profile,
			}
			logrus.Println("Saving new profile:", account.Profile.Id)
			account.Persist(*account.Profile)

			var nexusMessage messages.Message
			nexusMessage.Seq = message.Seq + 1
			nexusMessage.SeqReply = message.Seq
			nexusMessage.Event = events.UserAdded
			nexusMessage.Profile = message.Profile
			nexusMessage.Recipient = "hub"

			connectionmanager.SendMessage(&nexusMessage)
		}

		ProcessModules(message)
		CheckForUpdate(message)
	case events.PreferencesChanged:
		logrus.Info("received preferences_changed: ", message.Data.MinClientVersion)
		CheckForUpdate(message)
	}
}
