package routes

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nexus/hub"
)

func ServeWebSocket(w http.ResponseWriter, r *http.Request) {
	hub.Nexus.ServeWebSocket(w, r)
}
