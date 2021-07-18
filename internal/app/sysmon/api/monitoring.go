package api

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/app/sysmon/config"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"github.com/sirupsen/logrus"
)

type TemperatureNotifyData struct {
	Temperature float64
}

var notifyData TemperatureNotifyData

func NotifyTemperatureEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyTemperatureEndpoint")

	if !config.SysTemperatureConf.Enabled {
		logrus.Info("Temperature monitoring is disabled!")
		return
	}

	// Try to decode the request body into the struct. If there is an error,
	// respond to the client with the error message and a 400 status code.
	err := json.NewDecoder(r.Body).Decode(&notifyData)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	// TODO: If temperature > 60 -> send mail

	w.WriteHeader(200)

	// create metric to send to graphite
	metric := map[string]float64{
		"temperature": notifyData.Temperature,
	}

	prefix := config.SysTemperatureConf.GraphitePrefix + "." + config.Hostname
	graphite.SendMetric(metric, prefix)
}
