package monitoring

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/integrations/graphite"
)

type UptimeNotifyData struct {
	Agent string
	Key   string
	Value float64
}

func NotifyUptimeEndpoint(w http.ResponseWriter, r *http.Request) {
	// Try to decode the request body into the struct. If there is an error,
	// respond to the client with the error message and a 400 status code.
	var uptimeNotifyData UptimeNotifyData
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
	graphite.SendMetric(metric, "project6.agents."+uptimeNotifyData.Agent)
}
