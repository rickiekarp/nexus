package hub

import (
	"encoding/json"
	"fmt"

	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"git.rickiekarp.net/rickie/home/pkg/integrations/graphite"
	"github.com/sirupsen/logrus"
)

const statsMetricPrefix = "nexus.stats"

func sendHubQueueSizeMetric(sequence int64, size int64) {
	if config.NexusConf.Graphite.Enabled {
		graphite.SendMetric(
			map[string]float64{
				"hubQueueSize": float64(size),
			},
			statsMetricPrefix)
	} else {
		jsonMessage, _ := json.Marshal(&messages.Message{
			Seq:     sequence,
			Event:   events.Stats,
			Message: fmt.Sprintf("HUB_QUEUE_SIZE: %d", size),
		})
		logrus.Println(string(jsonMessage))
	}
}

func sendClientConnectionsMetric(sequence int64, clientConnections int) {
	if config.NexusConf.Graphite.Enabled {
		graphite.SendMetric(
			map[string]float64{
				"clientConnections": float64(clientConnections),
			},
			statsMetricPrefix)
	} else {
		jsonMessage, _ := json.Marshal(&messages.Message{
			Seq:     sequence,
			Event:   events.Stats,
			Message: fmt.Sprintf("CONNECTED_CLIENTS: %d", len(Nexus.Clients)),
		})
		logrus.Println(string(jsonMessage))
	}
}
