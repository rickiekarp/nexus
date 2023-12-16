package eventmanager

import (
	"fmt"
	"runtime"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"git.rickiekarp.net/rickie/home/internal/project6/config"
	"git.rickiekarp.net/rickie/home/pkg/network"
	"github.com/sirupsen/logrus"
)

func CheckForUpdate(message messages.Message) {
	if config.Version < *message.Data.MinClientVersion {
		logrus.Println("Version too old! Downloading new version...")
		err := network.DownloadFile(
			fmt.Sprintf("software/dev/project6/%s/project6", runtime.GOARCH),
			"project6svc_update")

		if err != nil {
			logrus.Error(err)
		}
	}
}
