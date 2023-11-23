package api

import (
	"git.rickiekarp.net/rickie/home/internal/nucleus/api/routes"
	"git.rickiekarp.net/rickie/home/internal/nucleus/channel"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"git.rickiekarp.net/rickie/home/internal/nucleus/mail"
	"git.rickiekarp.net/rickie/home/internal/nucleus/users"
	"git.rickiekarp.net/rickie/home/internal/nucleus/webpage"
	"github.com/gorilla/mux"
)

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {
	r.HandleFunc("/", routes.ServeHome)
	r.HandleFunc("/ws", routes.ServeWebSocket)
	r.HandleFunc("/version", routes.ServeVersion).Methods("GET")
	r.HandleFunc("/preferences", routes.PatchPreferencesChanged).Methods("PATCH")
	r.HandleFunc("/hub/v1/send", hub.SendMessage).Methods("POST")
	r.HandleFunc("/hub/v1/broadcast", hub.BroadcastMessage).Methods("POST")

	// login service
	r.HandleFunc("/users/v1/authorize", users.Authorize).Methods("POST")
	r.HandleFunc("/users/v1/login", users.Login).Methods("POST")
	r.HandleFunc("/users/v1/register", users.Register).Methods("POST")

	// webpage service
	r.HandleFunc("/webpage/v1/contact", webpage.ServeContactInfo).Methods("GET")
	r.HandleFunc("/webpage/v1/resume/experience", webpage.ServeExperience).Methods("GET")
	r.HandleFunc("/webpage/v1/resume/education", webpage.ServeEducation).Methods("GET")
	r.HandleFunc("/webpage/v1/resume/skills", webpage.ServeSkills).Methods("GET")

	// mail service
	r.HandleFunc("/mail/v2/notify", mail.Notify).Methods("POST")
	r.HandleFunc("/mail/v1/notify/reminders", mail.NotifyRemindersEndpoint).Methods("POST")

	r.HandleFunc("/weather/stop", channel.StopWeatherMonitorEndpoint).Methods("GET")
	r.HandleFunc("/weather/start", channel.StartWeatherMonitorEndpoint).Methods("GET")
	r.HandleFunc("/weather/status", channel.WeatherMonitorStatusEndpoint).Methods("GET")
	r.HandleFunc("/monitoring/notifyUptime", NotifyUptimeEndpoint).Methods("POST")
	r.HandleFunc("/monitoring/notifyTemperature", NotifyTemperatureEndpoint).Methods("POST")
}
