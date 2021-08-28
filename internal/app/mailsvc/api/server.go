package api

import (
	"net/http"

	"github.com/gorilla/mux"
)

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {
	r.HandleFunc("/v1/notify", NotifyEndpoint).Methods("POST")
	r.HandleFunc("/v1/fitness/remind", NotifyFitnessReminderEndpoint).Methods("POST")
	r.HandleFunc("/v1/sendReminders", NotifyRemindersEndpoint).Methods("POST")
}

// GetServer returns a http server that listens on the given addr
func GetMailServer(addr string) *http.Server {
	router := mux.NewRouter()
	defineApiEndpoints(router)

	return &http.Server{
		Addr:    addr,
		Handler: router,
	}
}
