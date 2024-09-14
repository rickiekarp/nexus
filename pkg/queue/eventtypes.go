package queue

type QueueEventType string

type HubQueueEventMessage struct {
	Event   QueueEventType `json:"event,omitempty"`
	Payload string         `json:"payload,omitempty"`
}
