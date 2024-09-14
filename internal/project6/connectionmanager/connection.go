package connectionmanager

import (
	"flag"
	"net/http"
	"net/url"

	"git.rickiekarp.net/rickie/home/internal/project6/config"
	"git.rickiekarp.net/rickie/home/pkg/nexuslib/account"
	nexuslib "git.rickiekarp.net/rickie/nexus-corelib"
	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

var host = flag.String("host", "localhost:12000", "target host")

var Connection *websocket.Conn

func Connect(profile *account.Account) {
	url := url.URL{Scheme: "ws", Host: *host, Path: "/ws"}
	logrus.Printf("connecting to %s (protocol: %s)", url.String(), config.CommunicationProtocol)

	webSockerDialer := websocket.DefaultDialer
	webSockerDialer.Subprotocols = []string{config.CommunicationProtocol}

	var headers = http.Header{}
	headers.Add(nexuslib.HeaderNexusClientVersion, config.Version)

	if profile != nil {
		headers.Add(nexuslib.HeaderNexusProfileId, profile.Id)
	}

	wsConn, _, err := webSockerDialer.Dial(url.String(), headers)
	if err != nil {
		logrus.Fatal("dial:", err)
	}

	Connection = wsConn
	account.Profile = profile
}
