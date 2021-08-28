package mail

import (
	"net/smtp"
	"strconv"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/config"
	"git.rickiekarp.net/rickie/home/pkg/models/mailmodel"
)

func SendMail(mailData mailmodel.MailData) error {
	// Set up authentication information.
	auth := smtp.PlainAuth("", config.MailConfig.Mail.Username, config.MailConfig.Mail.Password, config.MailConfig.Mail.Host)

	// Connect to the server, authenticate, set the sender and recipient,
	// and send the email all in one step.
	mime := "Content-Type: text/html; charset=\"UTF-8\";\n\n"
	msg := []byte("Subject: " + mailData.Subject + "\r\n" + mime + "\r\n" + mailData.Message + "\r\n")
	err := smtp.SendMail(
		config.MailConfig.Mail.Host+":"+strconv.Itoa(config.MailConfig.Mail.Port), auth, config.MailConfig.Mail.Username, []string{mailData.To}, msg)
	return err
}
