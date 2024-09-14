package hub

import (
	"time"
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
	}
}
