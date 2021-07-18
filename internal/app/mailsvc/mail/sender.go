package mail

import (
	"net/smtp"
	"strconv"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/config"
)

func SendMail(recipients []string, subject, message string) error {
	// Set up authentication information.
	auth := smtp.PlainAuth("", config.MailConfig.Mail.Username, config.MailConfig.Mail.Password, config.MailConfig.Mail.Host)

	// Connect to the server, authenticate, set the sender and recipient,
	// and send the email all in one step.
	msg := []byte("Subject: " + subject + "\r\n" + message + "\r\n")
	err := smtp.SendMail(
		config.MailConfig.Mail.Host+":"+strconv.Itoa(config.MailConfig.Mail.Port), auth, config.MailConfig.Mail.Username, recipients, msg)
	return err
}
