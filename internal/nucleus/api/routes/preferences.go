package routes

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"github.com/sirupsen/logrus"
)

func PatchPreferencesChanged(w http.ResponseWriter, r *http.Request) {

	// deserialize message
	var message messages.Message
	_ = json.NewDecoder(r.Body).Decode(&message)

	if message.Data == nil {
		logrus.Warn("Can not process message: ", r.Body)
		w.WriteHeader(400)
		return
	}

	// process message
	if message.Data.MinClientVersion != nil {
		logrus.Info("PreferencesChanged-MinClientVersion: ", config.NucleusConf.Project6.MinClientVersion, " -> ", *message.Data.MinClientVersion)
		config.NucleusConf.Project6.MinClientVersion = *message.Data.MinClientVersion
	}

	// broadcast preferences_changed event
	for client := range hub.Nucleus.Clients {
		msg := messages.Message{
			Event: events.PreferencesChanged,
			Data: &messages.MessageData{
				ServerVersion:    config.Version,
				MinClientVersion: &config.NucleusConf.Project6.MinClientVersion},
		}
		client.SendMessage(msg, true)
	}
	w.WriteHeader(204)
}
