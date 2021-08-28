package api

import (
	"bytes"
	"encoding/json"
	"fmt"
	"html/template"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/config"
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
	} else {
		logrus.Info("X-Notification-Token missing!")
		w.WriteHeader(401)
		return
	}

	if len(mailData.To) == 0 || len(mailData.Subject) == 0 || len(mailData.Message) == 0 {
		logrus.Error("MailData incomplete, can't send mail!")
		w.WriteHeader(500)
		return
	}

	err = mail.SendMail(mailData)

	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		w.WriteHeader(200)
	}
}

func NotifyFitnessReminderEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyFitnessReminderEndpoint")

	// TODO: Add check for notification token

	t, err := template.ParseFiles(config.ConfigBaseDir + "templates/fitnessReminder.html")
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}

	curDate := time.Now().Format("01-2006")
	config := map[string]string{
		"currentDate": curDate,
	}

	var tpl bytes.Buffer
	if err := t.Execute(&tpl, config); err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}

	result := tpl.String()

	data := mailmodel.MailData{
		To:      "rickie.karp@gmail.com",
		Subject: fmt.Sprintf("Fitness Tracking Reminder - %s", curDate),
		Message: result,
	}

	val, ok := r.Header["X-Notification-Token"]
	if ok {
		logrus.Info("X-Notification-Token key header is present with value ", val[0])

		// check if token is present in the database
		tokenData := datasource.GetApplicationSettingsNotificationTokenContent()
		if tokenData != nil {
			logrus.Info(*tokenData)
		}
	} else {
		logrus.Info("X-Notification-Token missing!")
		w.WriteHeader(401)
		return
	}

	if len(data.To) == 0 || len(data.Subject) == 0 || len(data.Message) == 0 {
		logrus.Error("MailData incomplete, can't send mail!")
		w.WriteHeader(500)
		return
	}

	err = mail.SendMail(data)

	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		w.WriteHeader(200)
	}
}
