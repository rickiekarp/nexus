package main

import (
	"flag"
	"log"
	"net"
	"net/http"
)

var addr = flag.String("addr", ":8080", "http service address")

var (
	Version          = "development"                                                // Version set during go build using ldflags
	ConfigBaseDir    = "deployments/module-deployment/values/launchpad/dev/config/" // ConfigBaseDir set during go build using ldflags
	ResourcesBaseDir = "web/launchpad/"
)

// Will formatting Message into JSON
type Message struct {
	//Message Struct
	Sender    string `json:"sender,omitempty"`
	Recipient string `json:"recipient,omitempty"`
	Content   string `json:"content,omitempty"`
	ServerIP  string `json:"serverIp,omitempty"`
	SenderIP  string `json:"senderIp,omitempty"`
}

// jsonMessage, _ := json.Marshal(&Message{Content: "/A new socket has connected. ", ServerIP: LocalIp(), SenderIP: conn.socket.RemoteAddr().String()})

func serveHome(w http.ResponseWriter, r *http.Request) {
	log.Println(r.URL)
	if r.URL.Path != "/" {
		http.Error(w, "Not found", 404)
		return
	}
	if r.Method != "GET" {
		http.Error(w, "Method not allowed", 405)
		return
	}
	http.ServeFile(w, r, "home.html")
}

func main() {
	log.Println("loading config...")
	flag.Parse()

	log.Println("creating hub...")
	hub := NewHub()

	log.Println("starting hub...")
	go hub.Run()

	http.HandleFunc("/", serveHome)
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		ServeWs(hub, w, r)
	})

	log.Println("hub started")
	err := http.ListenAndServe(*addr, nil)
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}
}

func LocalIp() string {
	address, _ := net.InterfaceAddrs()
	var ip = "localhost"
	for _, address := range address {
		if ipAddress, ok := address.(*net.IPNet); ok && !ipAddress.IP.IsLoopback() {
			if ipAddress.IP.To4() != nil {
				ip = ipAddress.IP.String()
			}
		}
	}
	return ip
}
