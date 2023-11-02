package events

type EventType string

const (
	Hello        EventType = "hello"
	Bye          EventType = "bye"
	ConfigChange EventType = "config_updated"
	Stats        EventType = "stats"
	Message      EventType = "message"
)
