package hub

// Will formatting Message into JSON
type Message struct {
	//Message Struct
	Seq       int64  `json:"seq,omitempty"`
	SeqReply  int64  `json:"seq_reply,omitempty"`
	Status    string `json:"status,omitempty"`
	Event     string `json:"event,omitempty"`
	Sender    string `json:"sender,omitempty"`
	Recipient string `json:"recipient,omitempty"`
	Action    string `json:"action,omitempty"`
	Content   string `json:"content,omitempty"`
	ServerIP  string `json:"serverIp,omitempty"`
	SenderIP  string `json:"senderIp,omitempty"`
}
