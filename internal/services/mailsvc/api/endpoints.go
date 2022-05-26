package api

import (
	"bytes"
	"encoding/json"
	"fmt"
	"html/template"
	"io/ioutil"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/models/mailmodel"
	"git.rickiekarp.net/rickie/home/internal/services/mailsvc/config"
	"git.rickiekarp.net/rickie/home/internal/services/mailsvc/datasource"
	"git.rickiekarp.net/rickie/home/internal/services/mailsvc/mail"
	"github.com/sirupsen/logrus"
)

func Notify(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyEndpoint")

	isValidToken := checkNotificationToken(w, r, "X-Notification-Token")
	if !isValidToken {
		return
	}

	var mailData mailmodel.MailData

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}
	bodyString := string(body)

	// Try to decode the request body into the struct. If there is an error,
	// respond to the client with the error message and a 400 status code.
	err = json.Unmarshal([]byte(bodyString), &mailData)
	if err != nil {
		logrus.Error(err)
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	// validate data
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

	isValidToken := checkNotificationToken(w, r, "X-Notification-Token")
	if !isValidToken {
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

func NotifyRemindersEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyRemindersEndpoint")

	curDate := time.Now().Format("01-2006")

	data := mailmodel.MailData{
		To:      "rickie.karp@gmail.com",
		Subject: fmt.Sprintf("Reminders - %s", curDate),
		Message: "Here are your reminders:<br>",
	}

	isValidToken := checkNotificationToken(w, r, "X-Notification-Token")
	if !isValidToken {
		return
	}

	isValidUserId, userId := checkUserIdHeader(w, r)
	if !isValidUserId {
		return
	}

	reminderData := datasource.GetActiveRemindersForUser(userId)

	if len(*reminderData) == 0 {
		logrus.Info("No reminder data found for userId ", userId)
		return
	}

	for _, elem := range *reminderData {
		data.Message += elem.Description + "<br>"
	}

	if len(data.To) == 0 || len(data.Subject) == 0 || len(data.Message) == 0 {
		logrus.Error("MailData incomplete, can't send mail!")
		w.WriteHeader(500)
		return
	}

	err := mail.SendMail(data)

	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		// update the reminder send date in the database
		for _, reminder := range *reminderData {
			err = datasource.SetReminderSendDateForReminderId(reminder.Id)
			if err != nil {
				logrus.Error(err)
			}
		}
		w.WriteHeader(200)
	}
}
