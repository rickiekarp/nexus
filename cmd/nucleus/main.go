package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"net/http"
	"os"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"github.com/sirupsen/logrus"
)

var nucleus *hub.Hub

func main() {
	logrus.Info("Starting Nucleus (" + config.Version + ")")

	// set up program flags
	flag.Parse()

	// load config files
	cfgError := config.ReadNucleusConfig()
	if cfgError != nil {
		logrus.Error("Could not load config!")
		os.Exit(1)
	}

	// set up nucleus
	nucleus = hub.NewHub()
	go nucleus.Run()

	// set up server routes
	http.HandleFunc("/", hub.ServeHome)
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		nucleus.ServeWebSocket(w, r)
	})

	// start monitoring
	go collectStats()

	// start application
	logrus.Info("Started Nucleus, awaiting SIGINT or SIGTERM")
	err := http.ListenAndServe(":12000", nil)
	if err != nil {
		logrus.Fatal("ListenAndServe: ", err)
	}
}

func collectStats() {
	ticker := time.NewTicker(1 * time.Minute)
	defer ticker.Stop()

	var sequence int64 = 0

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}

		clientCount := len(nucleus.Clients)

		if config.NucleusConf.Graphite.Enabled {
			graphite.SendMetric(
				map[string]float64{
					"seq":               float64(sequence),
					"clientConnections": float64(clientCount),
				},
				"nucleus.stats")
		} else {
			jsonMessage, _ := json.Marshal(&hub.Message{
				Seq:     sequence,
				Event:   "stats",
				Content: fmt.Sprintf("CONNECTED_CLIENTS: %d", len(nucleus.Clients)),
			})
			logrus.Println(string(jsonMessage))
		}
	}
}
