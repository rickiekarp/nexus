package api

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/sysmon/config"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"github.com/sirupsen/logrus"
)

type UptimeNotifyData struct {
	Agent string
	Key   string
	Value float64
}

var uptimeNotifyData UptimeNotifyData

func NotifyUptimeEndpoint(w http.ResponseWriter, r *http.Request) {

	if !config.SysTemperatureConf.Enabled {
		logrus.Info("Uptime monitoring is disabled!")
		return
	}

	// Try to decode the request body into the struct. If there is an error,
	// respond to the client with the error message and a 400 status code.
	err := json.NewDecoder(r.Body).Decode(&uptimeNotifyData)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	if uptimeNotifyData.Agent == "" {
		w.WriteHeader(http.StatusBadRequest)
		return
	}

	w.WriteHeader(200)

	// create metric to send to graphite
	metric := map[string]float64{
		uptimeNotifyData.Key: uptimeNotifyData.Value,
	}
	prefix := config.UptimeMonitoringConf.GraphitePrefix + "." + uptimeNotifyData.Agent
	graphite.SendMetric(metric, prefix)
}
