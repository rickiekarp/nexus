package mail

import (
	"bytes"
	"encoding/json"
	"fmt"
	"html/template"
	"io/ioutil"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/pkg/models/mailmodel"
	"github.com/sirupsen/logrus"
)

func Notify(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyEndpoint")

	isValidToken := checkNotificationToken(w, r, "X-Notification-Token")
	if !isValidToken {
		return
	}

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}
	bodyString := string(body)

	var mailData mailmodel.MailData

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

	err = SendMail(mailData)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		w.WriteHeader(200)
	}
}

func NotifyRemindersEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called NotifyRemindersEndpoint")

	isValidToken := checkNotificationToken(w, r, "X-Notification-Token")
	if !isValidToken {
		return
	}
	isValidUserId, userId := checkUserIdHeader(w, r)
	if !isValidUserId {
		return
	}

	reminderData := GetActiveRemindersForUser(userId)
	if len(*reminderData) == 0 {
		logrus.Info("No reminder data found for userId ", userId)
		return
	}

	var contentString string
	for _, elem := range *reminderData {
		contentString += elem.Description + "\n"
	}
	mailContent := map[string]string{
		"content": contentString,
	}

	template, err := template.ParseFiles(config.ConfigBaseDir + "templates/reminders.html")
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}

	var templateBuffer bytes.Buffer
	if err := template.Execute(&templateBuffer, mailContent); err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
		return
	}

	messageContent := templateBuffer.String()
	data := mailmodel.MailData{
		To:      config.NucleusConf.Mail.Notify.Recipient,
		Subject: fmt.Sprintf("ToDo - %s", time.Now().Format("2006-01-02")),
		Message: messageContent,
	}

	if len(data.To) == 0 || len(data.Subject) == 0 || len(data.Message) == 0 {
		logrus.Error("MailData incomplete, can't send mail!")
		w.WriteHeader(500)
		return
	}

	err = SendMail(data)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(500)
	} else {
		// update the reminder send date in the database
		for _, reminder := range *reminderData {
			err = SetReminderSendDateForReminderId(reminder.Id)
			if err != nil {
				logrus.Error(err)
			}
		}
		w.WriteHeader(200)
	}
}
