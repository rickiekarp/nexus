package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/launchpad/hub"
	"github.com/sirupsen/logrus"
)

var addr = flag.String("addr", ":12000", "http service address")

var launchPad *hub.Hub

var (
	Version          = "development"                                                // Version set during go build using ldflags
	ConfigBaseDir    = "deployments/module-deployment/values/launchpad/dev/config/" // ConfigBaseDir set during go build using ldflags
	ResourcesBaseDir = "web/launchpad/"
)

func serveHome(w http.ResponseWriter, r *http.Request) {
	logrus.Println(r.URL)
	if r.URL.Path != "/" {
		http.Error(w, "Not found", 404)
		return
	}
	if r.Method != "GET" {
		http.Error(w, "Method not allowed", 405)
		return
	}
	http.ServeFile(w, r, ResourcesBaseDir+"static/home.html")
}

func main() {
	logrus.Info("Starting Launchpad (" + Version + ")")

	flag.Parse()

	launchPad = hub.NewHub()
	go launchPad.Run()

	http.HandleFunc("/", serveHome)
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		launchPad.ServeWs(w, r)
	})

	go printStats()

	logrus.Info("Started Launchpad, awaiting SIGINT or SIGTERM")
	err := http.ListenAndServe(*addr, nil)
	if err != nil {
		logrus.Fatal("ListenAndServe: ", err)
	}
}

func printStats() {
	ticker := time.NewTicker(1 * time.Hour)
	defer ticker.Stop()

	var sequence int64 = 0

	for {
		select {
		case <-ticker.C:
			sequence += 1

			jsonMessage, _ := json.Marshal(&hub.Message{
				Seq:     sequence,
				Event:   "stats",
				Content: fmt.Sprintf("CONNECTED_CLIENTS: %d", len(launchPad.Clients)),
			})

			logrus.Println(string(jsonMessage))
		}
	}
}
