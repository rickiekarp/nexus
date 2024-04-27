package hub

import (
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/messages"
)

func ProcessEvent(client Client, nexusMessage messages.Message) {

	// todo check target of message

	if nexusMessage.Recipient == "hub" {
		return
	}

	if nexusMessage.Recipient == "broadcast" {
		SendMessageToClient(nexusMessage, nexusMessage.Recipient)
		return
	}

	SendMessageToClient(nexusMessage, client.Id)
}
