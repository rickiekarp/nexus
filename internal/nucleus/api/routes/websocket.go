package routes

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
)

func ServeWebSocket(w http.ResponseWriter, r *http.Request) {
	hub.Nucleus.ServeWebSocket(w, r)
}
