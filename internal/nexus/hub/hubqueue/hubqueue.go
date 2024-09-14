package hubqueue

import (
	"time"

	"git.rickiekarp.net/rickie/home/internal/nexus/hub"
	"git.rickiekarp.net/rickie/home/pkg/queue"
	"git.rickiekarp.net/rickie/home/pkg/util"
	"github.com/sirupsen/logrus"
)

var HubQueue *queue.Queue

func StartHubQueue() {

	ticker := time.NewTicker(6 * time.Second)
	defer ticker.Stop()

	HubQueue = queue.NewQueue()
	var sequence int64 = 0

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}
		sequence += 1

		if !HubQueue.IsEmpty() {
			elementsToProcess := util.RandomInt64(1, 10)
			if elementsToProcess > HubQueue.Size {
				elementsToProcess = HubQueue.Size
			}
			logrus.Println("Processing", elementsToProcess, "of", HubQueue.Size, "HubQueue elements")
			for i := int64(0); i < elementsToProcess; i++ {
				elem, _ := HubQueue.Pop()
				err := processMessage(elem)
				if err != nil {
					logrus.Error(err)
					continue
				}
			}
		}

		hub.SendHubQueueSizeMetric(sequence, HubQueue.Size)
	}
}
