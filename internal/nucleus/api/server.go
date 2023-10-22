package api

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	"github.com/gorilla/mux"
	"github.com/sirupsen/logrus"
)

const recipientHeader string = "X-Recipient-Client"

// defineApiEndpoints defines all routes the Router can handle
func defineApiEndpoints(r *mux.Router) {

	r.HandleFunc("/", ServeHome)
	r.HandleFunc("/ws", ServeWebSocket)
	r.HandleFunc("/v1/send", SendMessage).Methods("POST")
	r.HandleFunc("/v1/broadcast", BroadcastMessage).Methods("POST")
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

func ServeHome(w http.ResponseWriter, r *http.Request) {
	logrus.Println(r.URL)
	if r.URL.Path != "/" {
		http.Error(w, "Not found", http.StatusNotFound)
		return
	}
	if r.Method != "GET" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	http.ServeFile(w, r, config.ResourcesBaseDir+"static/home.html")
}

func ServeWebSocket(w http.ResponseWriter, r *http.Request) {
	hub.Nucleus.ServeWebSocket(w, r)
}
