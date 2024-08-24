package channel

import (
	"net/http"
	"os"

	"git.rickiekarp.net/rickie/home/pkg/util"
	"github.com/sirupsen/logrus"
)

func FetchVault(w http.ResponseWriter, r *http.Request) {

	vaultType, hasType := r.Header["X-Vault-Type"]
	if !hasType {
		logrus.Warn("X-Vault-Type missing!")
		w.WriteHeader(http.StatusUnauthorized)
		return
	}

	keyFormat, hasKeyType := r.Header["X-Vault-Keyformat"]
	if !hasKeyType {
		logrus.Warn("X-Vault-Keyformat missing!")
		w.WriteHeader(http.StatusUnauthorized)
		return
	}

	key, hasKey := r.Header["X-Vault-Key"]
	if !hasKey {
		logrus.Warn("X-Vault-Key missing!")
		w.WriteHeader(http.StatusUnauthorized)
		return
	}

	keyFileToFetch := "filestorage/vault/" + vaultType[0] + "/" + keyFormat[0] + "/" + key[0] + "." + keyFormat[0]
	logrus.Info("Call:FetchVault:", keyFileToFetch)

	if !util.Exists(keyFileToFetch) {
		logrus.Error("File does not exist: ", keyFileToFetch)
		w.WriteHeader(http.StatusServiceUnavailable)
		return
	}

	// load file for key
	fileBytes, err := os.ReadFile(keyFileToFetch)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(http.StatusInternalServerError)
		return
	}
	w.WriteHeader(http.StatusOK)
	w.Header().Set("Content-Type", "application/octet-stream")
	w.Write(fileBytes)
}
