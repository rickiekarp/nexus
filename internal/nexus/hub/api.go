package hub

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"git.rickiekarp.net/rickie/nexusform"
	"github.com/sirupsen/logrus"
)

func PatchPreferencesChanged(w http.ResponseWriter, r *http.Request) {

	// deserialize message
	var message nexusform.HubMessage
	_ = json.NewDecoder(r.Body).Decode(&message)

	if message.Data == nil {
		logrus.Warn("Can not process message: ", r.Body)
		w.WriteHeader(400)
		return
	}

	// process message
	if message.Data.MinClientVersion != nil {
		logrus.Info("PreferencesChanged-MinClientVersion: ", config.NexusConf.Project6.MinClientVersion, " -> ", *message.Data.MinClientVersion)
		config.NexusConf.Project6.MinClientVersion = *message.Data.MinClientVersion
	}

	// broadcast preferences_changed event
	for client := range Nexus.Clients {
		msg := nexusform.HubMessage{
			Event: nexusform.PreferencesChanged,
			Data: &nexusform.HubMessageData{
				ServerVersion:    config.Version,
				MinClientVersion: &config.NexusConf.Project6.MinClientVersion},
		}
		client.SendMessage(msg, true)
	}
	w.WriteHeader(204)
}
