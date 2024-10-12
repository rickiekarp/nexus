package hub

import (
	"git.rickiekarp.net/rickie/nexusform"
)

func ProcessEvent(client Client, nexusMessage nexusform.HubMessage) {

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
