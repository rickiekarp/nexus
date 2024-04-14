package eventmanager

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"git.rickiekarp.net/rickie/home/internal/project6/modules/filewatcher"
	"github.com/sirupsen/logrus"
)

func ProcessModules(message messages.Message) {
	for _, module := range message.Data.P6Module {
		if !module.Enabled {
			continue
		}

		logrus.Info("Processing modules")
		if module.Type == "filewatcher" {
			go filewatcher.Start(module.Observe, module.Logfile, module.Event)
		}
	}

}
