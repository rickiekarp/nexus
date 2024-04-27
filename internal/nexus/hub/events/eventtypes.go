package events

type EventType string

const (
	Hello              EventType = "hello"
	Bye                EventType = "bye"
	UserAdded          EventType = "user_added"
	PreferencesChanged EventType = "preferences_changed"
	Stats              EventType = "stats"
	Message            EventType = "message"
)
