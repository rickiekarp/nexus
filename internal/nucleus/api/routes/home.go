package routes

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"github.com/sirupsen/logrus"
)

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
