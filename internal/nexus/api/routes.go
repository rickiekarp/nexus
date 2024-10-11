package api

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/blockchain"
	"git.rickiekarp.net/rickie/home/internal/nexus/channel"
	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/hubqueue"
	"git.rickiekarp.net/rickie/home/internal/nexus/mail"
	"git.rickiekarp.net/rickie/home/internal/nexus/storage"
	"git.rickiekarp.net/rickie/home/internal/nexus/subsystems/monitoring"
	"git.rickiekarp.net/rickie/home/internal/nexus/subsystems/vault"
	"git.rickiekarp.net/rickie/home/internal/nexus/users"
	"git.rickiekarp.net/rickie/home/internal/nexus/webpage"
	"github.com/gorilla/mux"
)

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {
	r.HandleFunc("/", serveRoot)
	r.HandleFunc("/ws", serveWebSocket)
	r.HandleFunc("/version", serveVersion).Methods("GET")
	r.HandleFunc("/hub/v1/preferences", hub.PatchPreferencesChanged).Methods("PATCH")
	r.HandleFunc("/hub/v1/send", hub.SendMessage).Methods("POST")
	r.HandleFunc("/hub/v1/broadcast", hub.BroadcastMessage).Methods("POST")
	r.HandleFunc("/hub/v1/queue/push", hubqueue.PushToQueue).Methods("POST")

	// blockchain
	if config.NexusConf.NexusChain.Enabled {
		r.HandleFunc("/blockchain/get", blockchain.HandleGetBlockchain).Methods("GET")
		r.HandleFunc("/blockchain/addNode", blockchain.AddNewNodeToChain).Methods("GET")
		r.HandleFunc("/blockchain/addBlock", blockchain.AddNewBlock).Methods("GET")
	}

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

	// nexus extensions
	r.HandleFunc("/modules/weather/stop", channel.StopWeatherMonitorEndpoint).Methods("GET")
	r.HandleFunc("/modules/weather/start", channel.StartWeatherMonitorEndpoint).Methods("GET")
	r.HandleFunc("/modules/weather/status", channel.WeatherMonitorStatusEndpoint).Methods("GET")

	r.HandleFunc("/vault/v1/fetch/file", vault.FetchVaultFile).Methods("GET")
	r.HandleFunc("/vault/v1/fetch/key", vault.FetchVaultKey).Methods("GET")

	r.HandleFunc("/fileguardian/v1/fetch", storage.FetchFileGuard).Methods("POST") // DEPRECATED

	r.HandleFunc("/storage/v1/filelist/fetch", storage.FetchFilelistEntry).Methods("GET")
	r.HandleFunc("/storage/v1/fileguardian/fetch", storage.FetchFileGuard).Methods("POST")

	// monitoring
	r.HandleFunc("/monitoring/notifyUptime", monitoring.NotifyUptimeEndpoint).Methods("POST")
	r.HandleFunc("/monitoring/notifyTemperature", monitoring.NotifyTemperatureEndpoint).Methods("POST")
}
