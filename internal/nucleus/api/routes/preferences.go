package routes

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"github.com/sirupsen/logrus"
)

func PatchPreferencesChanged(w http.ResponseWriter, r *http.Request) {
	logrus.Println("upd: preferences_changed")

	for client := range hub.Nucleus.Clients {
		msg := messages.Message{
			Event: events.PreferencesChanged,
			Data: &messages.MessageData{
				ServerVersion:    config.Version,
				MinClientVersion: config.NucleusConf.Project6.MinClientVersion},
		}
		client.SendMessage(msg, true)
	}
	w.WriteHeader(200)
}
