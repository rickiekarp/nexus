package http

import (
	"context"
	"net/http"
	"time"

	"github.com/sirupsen/logrus"
)

// StartApiServer starts the given server and listens for requests
func StartApiServer(srv *http.Server) {
	logrus.Println("Starting API server")
	if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
		logrus.Error("listen: ", err)
	}
}

// StopApiServer tries to shut the given http server down gracefully
func StopApiServer(srv *http.Server) {
	logrus.Print("Stopping API server")

	ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	defer func() {
		cancel()
	}()

	if err := srv.Shutdown(ctx); err != nil {
		logrus.Print("API server shutdown failed: ", err)
	}
	logrus.Print("API server exited successfully")
}
