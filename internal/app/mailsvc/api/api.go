package api

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/mail"
	"github.com/sirupsen/logrus"
)

type MailData struct {
	To             string             `json:"to"`
	Subject        string             `json:"subject"`
	Message        string             `json:"message"`
	AdditionalData MailAdditionalData `json:"additionalData"`
}

type MailAdditionalData struct {
	Data string `json:"data"`
}

var mailData MailData

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
