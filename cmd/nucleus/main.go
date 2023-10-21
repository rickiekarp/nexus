package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"github.com/sirupsen/logrus"
)

var nucleus *hub.Hub

var (
	Version          = "development"                                              // Version set during go build using ldflags
	ConfigBaseDir    = "deployments/module-deployment/values/nucleus/dev/config/" // ConfigBaseDir set during go build using ldflags
	ResourcesBaseDir = "web/nucleus/"
)

func serveHome(w http.ResponseWriter, r *http.Request) {
	logrus.Println(r.URL)
	if r.URL.Path != "/" {
		http.Error(w, "Not found", http.StatusNotFound)
		return
	}
	if r.Method != "GET" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	http.ServeFile(w, r, ResourcesBaseDir+"static/home.html")
}

func main() {
	logrus.Info("Starting Nucleus (" + Version + ")")

	flag.Parse()

	nucleus = hub.NewHub()
	go nucleus.Run()

	http.HandleFunc("/", serveHome)
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
