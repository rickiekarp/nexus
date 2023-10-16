package mail

import (
	"bytes"
	"encoding/base64"
	"fmt"
	"io/ioutil"
	"mime/multipart"
	"net/http"
	"net/smtp"
	"path/filepath"
	"strconv"
	"strings"

	"git.rickiekarp.net/rickie/home/pkg/models/mailmodel"
	"git.rickiekarp.net/rickie/home/services/mailsvc/config"
	"github.com/sirupsen/logrus"
)

type Sender struct {
	auth smtp.Auth
}

type Message struct {
	To          []string
	CC          []string
	BCC         []string
	Subject     string
	Body        string
	Attachments map[string][]byte
}

func SendMail(mailData mailmodel.MailData) error {
	sender := new()
	m := newMessage(mailData.Subject, mailData.Message)
	m.To = []string{mailData.To}

	for _, attachment := range mailData.Attachments {
		m.attachFile(attachment)
	}

	err := sender.send(m)
	return err
}

func new() *Sender {
	auth := smtp.PlainAuth("", config.MailConfig.Mail.Username, config.MailConfig.Mail.Password, config.MailConfig.Mail.Host)
	return &Sender{auth}
}

func (s *Sender) send(m *Message) error {
	return smtp.SendMail(config.MailConfig.Mail.Host+":"+strconv.Itoa(config.MailConfig.Mail.Port), s.auth, config.MailConfig.Mail.Username, m.To, m.toBytes())
}

func newMessage(s, b string) *Message {
	return &Message{Subject: s, Body: b, Attachments: make(map[string][]byte)}
}

func (m *Message) attachFile(src string) error {
	b, err := ioutil.ReadFile(src)
	if err != nil {
		logrus.Error(err)
		return err
	}

	_, fileName := filepath.Split(src)
	m.Attachments[fileName] = b
	return nil
}

func (m *Message) toBytes() []byte {
	buf := bytes.NewBuffer(nil)
	withAttachments := len(m.Attachments) > 0
	buf.WriteString(fmt.Sprintf("Subject: %s\n", m.Subject))
	buf.WriteString(fmt.Sprintf("To: %s\n", strings.Join(m.To, ",")))
	if len(m.CC) > 0 {
		buf.WriteString(fmt.Sprintf("Cc: %s\n", strings.Join(m.CC, ",")))
	}

	if len(m.BCC) > 0 {
		buf.WriteString(fmt.Sprintf("Bcc: %s\n", strings.Join(m.BCC, ",")))
	}

	buf.WriteString("MIME-Version: 1.0\n")
	writer := multipart.NewWriter(buf)
	boundary := writer.Boundary()
	if withAttachments {
		buf.WriteString(fmt.Sprintf("Content-Type: multipart/mixed; boundary=%s\n", boundary))
		buf.WriteString(fmt.Sprintf("--%s\n", boundary))
	} else {
		buf.WriteString("Content-Type: text/html; charset=utf-8\n")
	}

	buf.WriteString(fmt.Sprintf("\n%s\n", m.Body))

	if withAttachments {
		// write boundary of body content in case there is an attachment
		buf.WriteString(fmt.Sprintf("--%s\n", boundary))

		for k, v := range m.Attachments {
			buf.WriteString(fmt.Sprintf("\n--%s\n", boundary))
			buf.WriteString(fmt.Sprintf("Content-Type: %s\n", http.DetectContentType(v)))
			buf.WriteString("Content-Transfer-Encoding: base64\n")
			buf.WriteString(fmt.Sprintf("Content-Disposition: attachment; filename=\"%s\"\n\n", k))

			b := make([]byte, base64.StdEncoding.EncodedLen(len(v)))
			base64.StdEncoding.Encode(b, v)
			buf.Write(b)
			buf.WriteString(fmt.Sprintf("\n--%s", boundary))
		}

		buf.WriteString("--")
	}

	return buf.Bytes()
}
