package hub

import "git.rickiekarp.net/rickie/nexusform"

var Nexus *Hub

// Hub maintains the set of active clients and broadcasts messages to the clients
type Hub struct {
	// Registered clients
	Clients map[*Client]bool

	// Inbound messages from the clients
	broadcast chan nexusform.HubMessage

	// Register requests from the clients
	register chan *Client

	// Unregister requests from clients
	unregister chan *Client
}

func NewHub() *Hub {
	return &Hub{
		broadcast:  make(chan nexusform.HubMessage),
		register:   make(chan *Client),
		unregister: make(chan *Client),
		Clients:    make(map[*Client]bool),
	}
}

func (h *Hub) Run() {
	for {
		select {
		case client := <-h.register:
			h.Clients[client] = true
		case client := <-h.unregister:
			if _, ok := h.Clients[client]; ok {
				delete(h.Clients, client)
				close(client.Send)
			}
		case message := <-h.broadcast:
			for client := range h.Clients {
				select {
				case client.Send <- message:
				default:
					close(client.Send)
					delete(h.Clients, client)
				}
			}
		}
	}
}
