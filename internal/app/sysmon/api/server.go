package api

import (
	"context"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/app/sysmon/channel"
	"github.com/gorilla/mux"
	"github.com/sirupsen/logrus"
)

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {
	r.HandleFunc("/weather/stop", channel.StopWeatherMonitorEndpoint).Methods("GET")
	r.HandleFunc("/weather/start", channel.StartWeatherMonitorEndpoint).Methods("GET")
	r.HandleFunc("/weather/status", channel.WeatherMonitorStatusEndpoint).Methods("GET")
	r.HandleFunc("/monitoring/notifyTemperature", NotifyTemperatureEndpoint).Methods("POST")
}

// GetServer returns a http server that listens on the given addr
func GetServer(addr string) *http.Server {
	router := mux.NewRouter()
	defineApiEndpoints(router)

	return &http.Server{
		Addr:    addr,
		Handler: router,
	}
}

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
