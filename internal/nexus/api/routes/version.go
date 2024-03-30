package routes

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nexus/config"
)

type Version struct {
	Version          string `json:"version"`
	Build            string `json:"build"`
	MinClientVersion string `json:"minClientVersion"`
}

func ServeVersion(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	var version Version
	_ = json.NewDecoder(r.Body).Decode(&version)

	version.Version = config.Version
	version.Build = config.Build
	version.MinClientVersion = config.NexusConf.Project6.MinClientVersion

	json.NewEncoder(w).Encode(version)
}
