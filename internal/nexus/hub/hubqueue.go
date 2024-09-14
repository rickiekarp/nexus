package hub

import (
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/pkg/queue"
	"git.rickiekarp.net/rickie/home/pkg/util"
	"github.com/sirupsen/logrus"
)

var HubQueue *queue.Queue

func StartHubQueue() {

	ticker := time.NewTicker(5 * time.Second)
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
				HubQueue.Pop()
			}
		}

		sendHubQueueSizeMetric(sequence, HubQueue.Size)
	}
}

func PushToQueue(w http.ResponseWriter, r *http.Request) {

	// deserialize message
	// var message messages.Message
	// _ = json.NewDecoder(r.Body).Decode(&message)

	// if message.Data == nil {
	// 	logrus.Warn("Can not process message: ", r.Body)
	// 	w.WriteHeader(400)
	// 	return
	// }

	elem := util.RandomInt(1, 1000)

	logrus.Info("API:PushToQueue:", elem)

	HubQueue.Push(queue.MyQueueElement{Metric: int64(elem)})

	w.WriteHeader(204)
}
