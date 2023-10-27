package api

import (
	"net/http"

	"github.com/gorilla/mux"
)

// GetServer returns a http server that listens on the given addr
func GetServer(addr string) *http.Server {
	router := mux.NewRouter()
	defineApiEndpoints(router)

	return &http.Server{
		Addr:    addr,
		Handler: router,
	}
}
