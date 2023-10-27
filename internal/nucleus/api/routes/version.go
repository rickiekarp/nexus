package routes

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
)

type Version struct {
	Version          string `json:"version"`
	MinClientVersion string `json:"minClientVersion"`
}

func ServeVersion(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	var version Version
	_ = json.NewDecoder(r.Body).Decode(&version)

	version.Version = config.Version
	version.MinClientVersion = config.NucleusConf.Project6.MinClientVersion

	json.NewEncoder(w).Encode(version)
}
