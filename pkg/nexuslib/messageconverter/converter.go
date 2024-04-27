package messageconverter

import (
	"encoding/json"

	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
	"github.com/sirupsen/logrus"
)

func ConvertToMessage(receivedMessageBytes []byte) *messages.Message {
	var nexusMessage messages.Message
	err := json.Unmarshal([]byte(receivedMessageBytes), &nexusMessage)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	return &nexusMessage
}
