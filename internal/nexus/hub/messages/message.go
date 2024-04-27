package messages

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/events"
	"git.rickiekarp.net/rickie/home/pkg/models/nexusmodel"
)

type Message struct {
	Seq       int64            `json:"seq,omitempty"`
	SeqReply  int64            `json:"seq_reply,omitempty"`
	Event     events.EventType `json:"event,omitempty"`
	Data      *MessageData     `json:"data,omitempty"`
	Message   string           `json:"message,omitempty"`
	Profile   string           `json:"profile,omitempty"`
	Recipient string           `json:"recipient,omitempty"`
	Status    string           `json:"status,omitempty"`
	ServerIP  string           `json:"serverIp,omitempty"`
	ClientIP  string           `json:"clientIp,omitempty"`
}

type MessageData struct {
	ServerVersion    string                `json:"serverVersion,omitempty"`
	MinClientVersion *string               `json:"minClientVersion,omitempty"`
	P6Module         []nexusmodel.P6Module `json:"modules,omitempty"`
}
