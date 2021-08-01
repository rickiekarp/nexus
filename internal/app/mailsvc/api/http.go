package api

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/datasource"
	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/mail"
	"git.rickiekarp.net/rickie/home/pkg/models/mailmodel"
	"github.com/sirupsen/logrus"
)

var mailData mailmodel.MailData

func NotifyEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyEndpoint")

	// TODO: Add check for notification token

	// Try to decode the request body into the struct. If there is an error,
	// respond to the client with the error message and a 400 status code.
	err := json.NewDecoder(r.Body).Decode(&mailData)
	if err != nil {
		logrus.Error(err)
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	val, ok := r.Header["X-Notification-Token"]
	if ok {
		logrus.Info("X-Notification-Token key header is present with value %s\n", val[0])

		// check if token is present in the database
		tokenData := datasource.GetApplicationSettingsNotificationTokenContent()
		if tokenData != nil {
			logrus.Info(*tokenData)
		}
	}

	if len(mailData.To) == 0 || len(mailData.Subject) == 0 || len(mailData.Message) == 0 {
		logrus.Error("MailData incomplete, can't send mail!")
		w.WriteHeader(500)
		return
	}

	err = mail.SendMail([]string{mailData.To},
		mailData.Subject,
		mailData.Message)

	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		w.WriteHeader(200)
	}
}
