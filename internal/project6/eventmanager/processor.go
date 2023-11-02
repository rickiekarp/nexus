package eventmanager

import (
	"fmt"
	"runtime"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"git.rickiekarp.net/rickie/home/internal/project6/config"
	"git.rickiekarp.net/rickie/home/pkg/network"
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
			logrus.Println("Update available! Downloading...")
			err := network.DownloadFile(
				fmt.Sprintf("software/dev/project6/%s/project6", runtime.GOARCH),
				"project6svc_update")

			if err != nil {
				logrus.Error(err)
			}
		}
		// TODO
	case events.ConfigChange:
		logrus.Info("checking config: ", message.Event)
		// TODO
	}
}
