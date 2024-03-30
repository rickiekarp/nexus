package events

type EventType string

const (
	Hello              EventType = "hello"
	Bye                EventType = "bye"
	PreferencesChanged EventType = "preferences_changed"
	Stats              EventType = "stats"
	Message            EventType = "message"
)
