package hub

import (
	"encoding/json"
	"fmt"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"git.rickiekarp.net/rickie/home/pkg/integrations/graphite"
	"github.com/sirupsen/logrus"
)

func CollectStats() {
	ticker := time.NewTicker(1 * time.Minute)
	defer ticker.Stop()

	var sequence int64 = 0

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}

		sequence += 1

		clientCount := len(Nexus.Clients)

		if config.NexusConf.Graphite.Enabled {
			graphite.SendMetric(
				map[string]float64{
					"clientConnections": float64(clientCount),
				},
				"nexus.stats")
		} else {
			jsonMessage, _ := json.Marshal(&messages.Message{
				Seq:     sequence,
				Event:   events.Stats,
				Message: fmt.Sprintf("CONNECTED_CLIENTS: %d", len(Nexus.Clients)),
			})
			logrus.Println(string(jsonMessage))
		}
	}
}
