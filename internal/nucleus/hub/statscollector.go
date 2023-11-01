package hub

import (
	"encoding/json"
	"fmt"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
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

		clientCount := len(Nucleus.Clients)

		if config.NucleusConf.Graphite.Enabled {
			graphite.SendMetric(
				map[string]float64{
					"clientConnections": float64(clientCount),
				},
				"nucleus.stats")
		} else {
			jsonMessage, _ := json.Marshal(&messages.Message{
				Seq:     sequence,
				Event:   events.Stats,
				Content: fmt.Sprintf("CONNECTED_CLIENTS: %d", len(Nucleus.Clients)),
			})
			logrus.Println(string(jsonMessage))
		}
	}
}
