package hubqueue

import (
	"encoding/json"

	"git.rickiekarp.net/rickie/home/internal/nexus/storage"
	"git.rickiekarp.net/rickie/home/pkg/models/nexusmodel"
	"git.rickiekarp.net/rickie/home/pkg/queue"
	"git.rickiekarp.net/rickie/home/pkg/util"
	"github.com/sirupsen/logrus"
)

func processMessage(message queue.HubQueueEventMessage) error {
	logrus.Info("Processing message: ", message)

	switch message.Event {
	case FilestoreAdd:
		// convert
		fileEvent, err := convertEventMessage(message, nexusmodel.FileStorageEventMessage{})
		if err != nil {
			logrus.Error(err)
			return err
		}

		// set checksums for message
		fileChecksum := string(fileEvent.Sha1())
		fileEvent.FileHash = &fileChecksum
		checksum := util.CalcSha1(fileEvent.Path + "/" + *fileEvent.FileHash)
		fileEvent.Checksum = &checksum

		// process
		file := storage.FindFileInStorageByChecksum(*fileEvent.Checksum)
		if file == nil {
			storage.InsertFile(*fileEvent)
		} else {
			logrus.Info("Updating existing file in storage: ", *file.Id, " - ", *file.Checksum)
			storage.UpdateFileIteration(*file)
		}

	case FilestoreAdditionalDataUpdate:
		logrus.Warn("not implemented yet")

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
