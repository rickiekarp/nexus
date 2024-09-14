package hubqueue

import (
	"encoding/json"
	"errors"

	"git.rickiekarp.net/rickie/home/pkg/queue"
	"github.com/sirupsen/logrus"
)

func processMessage(message queue.HubQueueEventMessage) error {
	logrus.Info("Processing message: ", message)

	switch message.Event {
	case FilestoreAdd:
		// convert
		res, err := convertEventMessage(message, FileStorageEventMessage{})
		if err != nil {
			logrus.Error(err)
			return err
		}

		// validate
		if len(res.Checksum) == 0 {
			return errors.New("no checksum given")
		}

		// process
		file := FindFileInStorage(res.Checksum)
		if file == nil {
			InsertFile(*res)
		} else {
			logrus.Info("File already exists in storage: ", *file.Id, " - ", file.Checksum)
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
