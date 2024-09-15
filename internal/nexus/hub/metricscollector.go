package hub

import (
	"time"

	"git.rickiekarp.net/rickie/home/internal/nexus/hub/hubqueue"
)

func StartMetricsCollector() {
	ticker := time.NewTicker(1 * time.Minute)
	defer ticker.Stop()

	var sequence int64 = 0

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}
		sequence += 1

		// report client connections metric
		clientConnections := len(Nexus.Clients)
		sendClientConnectionsMetric(sequence, clientConnections)

		// report HubQueue metric
		if hubqueue.HubQueue != nil {
			hubQueueSize := hubqueue.HubQueue.Size
			SendHubQueueSizeMetric(sequence, int64(hubQueueSize))
		}
	}
}
