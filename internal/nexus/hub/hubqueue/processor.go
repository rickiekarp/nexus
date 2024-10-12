package hubqueue

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/storage"
	"git.rickiekarp.net/rickie/home/pkg/util"
	"git.rickiekarp.net/rickie/nexusform"
	"github.com/sirupsen/logrus"
)

func processMessage(message nexusform.HubQueueEventMessage) error {
	logrus.Info("Processing message: ", message)

	switch message.Event {
	case nexusform.FilestoreAdd:
		// convert
		fileEvent, err := nexusform.ConvertEventMessage(message, nexusform.FileListEntry{})
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

	case nexusform.FilestoreAdditionalDataUpdate:
		logrus.Warn("not implemented yet")

	default:
		logrus.Warn("ignoring invalid HubQueue event: ", message)
	}

	return nil
}
