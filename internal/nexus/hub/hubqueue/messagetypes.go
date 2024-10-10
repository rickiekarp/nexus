package hubqueue

import (
	"git.rickiekarp.net/rickie/home/pkg/models/nexusmodel"
	"git.rickiekarp.net/rickie/home/pkg/queue"
)

const (
	FilestoreAdd       queue.QueueEventType = "filestore_add"
	FilestoreAddExtras queue.QueueEventType = "filestore_add_extras"
)

type IQueueEventMessage interface {
	nexusmodel.FileStorageEventMessage | nexusmodel.FileStorageAdditionalDataEventMessage
}
