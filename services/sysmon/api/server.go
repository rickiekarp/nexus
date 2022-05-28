package api

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/services/sysmon/channel"
	"github.com/gorilla/mux"
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
