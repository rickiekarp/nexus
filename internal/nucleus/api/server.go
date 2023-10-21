package api

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"github.com/gorilla/mux"
)

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {

	r.HandleFunc("/", ServeHome)
	r.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		hub.Nucleus.ServeWebSocket(w, r)
	})
}

// GetServer returns a http server that listens on the given addr
func GetServer(addr string) *http.Server {
	router := mux.NewRouter()
	defineApiEndpoints(router)

	return &http.Server{
		Addr:    addr,
		Handler: router,
	}
}
