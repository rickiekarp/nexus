package mailmodel

type MailData struct {
	FromName    string   `json:"from_name"`
	To          string   `json:"to"`
	Subject     string   `json:"subject"`
	Message     string   `json:"message"`
	Attachments []string `json:"attachments"`
}
