package storage

import (
	"encoding/json"
	"net/http"

	"github.com/sirupsen/logrus"
)

func FetchFilelistEntry(w http.ResponseWriter, r *http.Request) {
	logrus.Info("CALL:API:FetchFilelistEntry")

	hash := r.URL.Query().Get("hash")
	if hash != "" {
		file := FindFileInStorageByFileHash(hash)
		if file == nil {
			w.WriteHeader(404)
			return
		} else {
			w.WriteHeader(200)
			json.NewEncoder(w).Encode(file)
			return
		}
	}

	checksum := r.URL.Query().Get("checksum")
	if checksum != "" {
		file := FindFileInStorageByChecksum(checksum)
		if file == nil {
			w.WriteHeader(404)
			return
		} else {
			w.WriteHeader(200)
			json.NewEncoder(w).Encode(file)
			return
		}
	}

	w.WriteHeader(400)
}
