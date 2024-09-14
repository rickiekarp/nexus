package api

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub"
	"github.com/sirupsen/logrus"
)

func serveRoot(w http.ResponseWriter, r *http.Request) {
	logrus.Println(r.URL)
	if r.URL.Path != "/" {
		http.Error(w, "Not found", http.StatusNotFound)
		return
	}
	if r.Method != "GET" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	http.ServeFile(w, r, config.ResourcesBaseDir+"static/home.html")
}

func serveWebSocket(w http.ResponseWriter, r *http.Request) {
	hub.Nexus.ServeWebSocket(w, r)
}

func serveVersion(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	var version Version
	_ = json.NewDecoder(r.Body).Decode(&version)

	version.Version = config.Version
	version.Build = config.Build
	version.MinClientVersion = config.NexusConf.Project6.MinClientVersion

	json.NewEncoder(w).Encode(version)
}
