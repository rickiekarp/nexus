package hub

import (
	"math/rand/v2"
	"time"

	"git.rickiekarp.net/rickie/home/pkg/queue"
	"github.com/sirupsen/logrus"
)

var hubQueue *queue.Queue

func StartHubQueue() {

	ticker := time.NewTicker(1 * time.Minute)
	defer ticker.Stop()

	hubQueue = queue.NewQueue()
	var sequence int64 = 0

	// add temporary data to process
	for i := 0; i < randRange(100, 200); i++ {
		hubQueue.Push(queue.MyQueueElement{
			Metric: sequence,
		})
	}

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}
		sequence += 1

		if !hubQueue.IsEmpty() {
			elementsToProcess := randRange(1, 10)
			logrus.Println("Processing", elementsToProcess, "HubQueue elements")
			for i := 0; i < elementsToProcess; i++ {
				hubQueue.Pop()
			}
		}

		sendHubQueueSizeMetric(sequence, hubQueue.Size)
	}
}

func randRange(min, max int) int {
	return rand.IntN(max-min) + min
}
