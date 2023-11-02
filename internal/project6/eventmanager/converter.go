package eventmanager

import (
	"encoding/json"

	"git.rickiekarp.net/rickie/home/internal/nucleus/hub/messages"
	"github.com/sirupsen/logrus"
)

func convertToMessage(receivedMessageBytes []byte) *messages.Message {
	var nucleusMessage messages.Message
	err := json.Unmarshal([]byte(receivedMessageBytes), &nucleusMessage)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	return &nucleusMessage
}
