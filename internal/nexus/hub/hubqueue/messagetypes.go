package hubqueue

import (
	"git.rickiekarp.net/rickie/home/pkg/models/nexusmodel"
	"git.rickiekarp.net/rickie/home/pkg/queue"
)

const (
	FilestoreAdd                  queue.QueueEventType = "filestore_add"
	FilestoreAdditionalDataUpdate queue.QueueEventType = "filestore_additional_data_update"
)

type IQueueEventMessage interface {
	nexusmodel.FileStorageEventMessage | nexusmodel.FileStorageAdditionalDataEventMessage
}
