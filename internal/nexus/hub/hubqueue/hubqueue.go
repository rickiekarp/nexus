package hubqueue

import (
	"time"

	"git.rickiekarp.net/rickie/home/pkg/queue"
	"github.com/sirupsen/logrus"
)

var HubQueue *queue.Queue

func StartHubQueue() {

	ticker := time.NewTicker(60 * time.Second)
	defer ticker.Stop()

	HubQueue = queue.NewQueue()

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}

		if !HubQueue.IsEmpty() {
			elementsToProcess := queue.QUEUE_PROCESSING_BATCH_COUNT
			if elementsToProcess > HubQueue.Size {
				elementsToProcess = HubQueue.Size
			} else {
				elementsToProcess = queue.QUEUE_PROCESSING_BATCH_COUNT
			}
			logrus.Println("Processing", elementsToProcess, "of", HubQueue.Size, "HubQueue elements")
			for i := 0; i < elementsToProcess; i++ {
				elem, wasPopped := HubQueue.Pop()
				if wasPopped {
					err := processMessage(*elem)
					if err != nil {
						logrus.Error(err)
						continue
					}
				}
			}
		}
	}
}
