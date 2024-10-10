package hubqueue

import (
	"encoding/json"
	"errors"

	"git.rickiekarp.net/rickie/home/internal/nexus/storage"
	"git.rickiekarp.net/rickie/home/pkg/models/nexusmodel"
	"git.rickiekarp.net/rickie/home/pkg/queue"
	"github.com/sirupsen/logrus"
)

func processMessage(message queue.HubQueueEventMessage) error {
	logrus.Info("Processing message: ", message)

	switch message.Event {
	case FilestoreAdd:
		// convert
		res, err := convertEventMessage(message, nexusmodel.FileStorageEventMessage{})
		if err != nil {
			logrus.Error(err)
			return err
		}

		// validate
		if len(res.Checksum) == 0 {
			return errors.New("no checksum given")
		}

		// process
		file := storage.FindFileInStorage(res.Checksum)
		if file == nil {
			storage.InsertFile(*res)
		} else {
			logrus.Info("Updating existing file in storage: ", *file.Id, " - ", file.Checksum)
			storage.UpdateFileIteration(*file)
		}

	default:
		logrus.Warn("ignoring invalid HubQueue event: ", message)
	}

	return nil
}

func convertEventMessage[V IQueueEventMessage](message queue.HubQueueEventMessage, eventMessage V) (*V, error) {
	err := json.Unmarshal([]byte(message.Payload), &eventMessage)
	if err != nil {
		return nil, err
	}
	return &eventMessage, nil
}
