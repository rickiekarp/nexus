package api

import (
	"encoding/json"
	"fmt"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/pkg/models/mailmodel"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"git.rickiekarp.net/rickie/home/pkg/network"
	"github.com/sirupsen/logrus"
)

type TemperatureNotifyData struct {
	Temperature float64
}

var temperatureNotifyData TemperatureNotifyData

func NotifyTemperatureEndpoint(w http.ResponseWriter, r *http.Request) {

	if !config.SysTemperatureConf.Enabled {
		logrus.Info("Temperature monitoring is disabled!")
		return
	}

	// Try to decode the request body into the struct. If there is an error,
	// respond to the client with the error message and a 400 status code.
	err := json.NewDecoder(r.Body).Decode(&temperatureNotifyData)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	w.WriteHeader(200)

	// create metric to send to graphite
	metric := map[string]float64{
		"temperature": temperatureNotifyData.Temperature,
	}
	prefix := config.SysTemperatureConf.GraphitePrefix + "." + config.Hostname
	graphite.SendMetric(metric, prefix)

	// If temperature >= 60 -> send mail
	if temperatureNotifyData.Temperature >= config.SysTemperatureConf.AlertThreshold {
		logrus.Info("Temperature reached ", temperatureNotifyData.Temperature, " degrees, sending notify!")
		headers := map[string]string{
			"X-Notification-Token": config.SysTemperatureConf.NotificationToken,
		}

		data := mailmodel.MailData{
			To:      config.SysTemperatureConf.NotifyEmailRecipient,
			Subject: "[Warning] " + config.Hostname + " temperature too high!",
			Message: "Please check the machine status! (Temperature: " + fmt.Sprintf("%f", temperatureNotifyData.Temperature) + ")",
		}
		err := network.Post(config.SysTemperatureConf.NotifyApiUrl, headers, data)
		if err != nil {
			logrus.Error("Could not send request to ", config.SysTemperatureConf.NotifyApiUrl)
		}
	}
}
