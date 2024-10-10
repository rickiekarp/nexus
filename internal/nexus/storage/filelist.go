package storage

import (
	"net/http"

	"github.com/sirupsen/logrus"
)

func FetchFilelistFiles(w http.ResponseWriter, r *http.Request) {
	logrus.Info("CALL:API:FetchFilelistFiles")
}
