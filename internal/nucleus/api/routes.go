package api

import (
	"git.rickiekarp.net/rickie/home/internal/nucleus/api/routes"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"github.com/gorilla/mux"
)

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {
	r.HandleFunc("/", routes.ServeHome)
	r.HandleFunc("/ws", routes.ServeWebSocket)
	r.HandleFunc("/version", routes.ServeVersion).Methods("GET")
	r.HandleFunc("/hub/v1/send", hub.SendMessage).Methods("POST")
	r.HandleFunc("/hub/v1/broadcast", hub.BroadcastMessage).Methods("POST")
}
