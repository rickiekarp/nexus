package messages

import "git.rickiekarp.net/rickie/home/internal/nucleus/hub/events"

type Message struct {
	Seq       int64            `json:"seq,omitempty"`
	SeqReply  int64            `json:"seq_reply,omitempty"`
	Event     events.EventType `json:"event,omitempty"`
	Data      *MessageData     `json:"data,omitempty"`
	Action    string           `json:"action,omitempty"`
	Content   string           `json:"content,omitempty"`
	Sender    string           `json:"sender,omitempty"`
	Recipient string           `json:"recipient,omitempty"`
	Status    string           `json:"status,omitempty"`
	ServerIP  string           `json:"serverIp,omitempty"`
	SenderIP  string           `json:"senderIp,omitempty"`
}

type MessageData struct {
	ServerVersion    string  `json:"serverVersion,omitempty"`
	MinClientVersion *string `json:"minClientVersion,omitempty"`
}
