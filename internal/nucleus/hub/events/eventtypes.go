package events

type EventType string

const (
	Hello   EventType = "hello"
	Bye     EventType = "bye"
	Stats   EventType = "stats"
	Message EventType = "message"
)
