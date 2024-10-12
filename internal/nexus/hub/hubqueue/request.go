package hubqueue

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/nexusform"
	"github.com/sirupsen/logrus"
)

func PushToQueue(w http.ResponseWriter, r *http.Request) {

	// deserialize message
	var message nexusform.HubQueueEventMessage
	err := json.NewDecoder(r.Body).Decode(&message)
	if err != nil {
		logrus.Error(err)
		w.WriteHeader(400)
		return
	}

	// push event to queue
	logrus.Info("API:PushToQueue:Event:", message.Event)
	wasPushed := HubQueue.Push(message)
	if wasPushed {
		w.WriteHeader(201)
	} else {
		w.WriteHeader(500)
	}
}
