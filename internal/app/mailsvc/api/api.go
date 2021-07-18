package api

import (
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/mail"
	"github.com/sirupsen/logrus"
)

func NotifyEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyEndpoint")

	// TODO: Add check for notification token

	err := mail.SendMail([]string{"rickie.karp@gmail.com"},
		"Test subject",
		"This is the email body.")

	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		w.WriteHeader(200)
	}
}
