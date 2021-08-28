package mailmodel

type MailData struct {
	To      string `json:"to"`
	Subject string `json:"subject"`
	Message string `json:"message"`
}
