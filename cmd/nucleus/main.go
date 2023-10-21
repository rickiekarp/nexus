package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"github.com/sirupsen/logrus"
)

var nucleus *hub.Hub

func main() {
	logrus.Info("Starting Nucleus (" + config.Version + ")")

	flag.Parse()

	nucleus = hub.NewHub()
	go nucleus.Run()

	http.HandleFunc("/", hub.ServeHome)
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		nucleus.ServeWebSocket(w, r)
	})

	go printStats()

	logrus.Info("Started Nucleus, awaiting SIGINT or SIGTERM")
	err := http.ListenAndServe(":12000", nil)
	if err != nil {
		logrus.Fatal("ListenAndServe: ", err)
	}
}

func printStats() {
	ticker := time.NewTicker(1 * time.Hour)
	defer ticker.Stop()

	var sequence int64 = 0

	for {
		_, ok := <-ticker.C
		if !ok {
			break
		}

		sequence += 1

		jsonMessage, _ := json.Marshal(&hub.Message{
			Seq:     sequence,
			Event:   "stats",
			Content: fmt.Sprintf("CONNECTED_CLIENTS: %d", len(nucleus.Clients)),
		})

		logrus.Println(string(jsonMessage))
	}
}
