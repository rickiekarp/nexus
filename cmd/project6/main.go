package main

import (
	"flag"
	"net/url"
	"os"
	"os/signal"
	"time"

	"git.rickiekarp.net/rickie/home/internal/project6/config"
	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

var clientId = flag.String("clientId", "project6", "unique identifier")
var host = flag.String("host", "localhost:12000", "target host")

func init() {
	flag.Parse()

	logrus.Info("Starting Project6 (" + config.Version + ")")
}

func main() {

	interrupt := make(chan os.Signal, 1)
	signal.Notify(interrupt, os.Interrupt)

	url := url.URL{Scheme: "ws", Host: *host, Path: "/ws"}
	logrus.Printf("connecting to %s (protocol: %s)", url.String(), *clientId)

	webSockerDialer := websocket.DefaultDialer
	webSockerDialer.Subprotocols = []string{*clientId}

	c, _, err := webSockerDialer.Dial(url.String(), nil)
	if err != nil {
		logrus.Fatal("dial:", err)
	}
	defer c.Close()

	done := make(chan struct{})

	go func() {
		defer close(done)
		for {
			_, message, err := c.ReadMessage()
			if err != nil {
				logrus.Println("read:", err)
				return
			}
			logrus.Printf("recv: %s", message)
		}
	}()

	ticker := time.NewTicker(10 * time.Second)
	defer ticker.Stop()

	for {
		select {
		case <-done:
			return
		// case t := <-ticker.C:
		// 	err := c.WriteMessage(websocket.TextMessage, []byte(t.String()))
		// 	if err != nil {
		// 		logrus.Println("writeError:", err)
		// 		return
		// 	} else {
		// 		logrus.Println("send:", t.String())
		// 	}
		case <-interrupt:
			logrus.Println("interrupt")

			// Cleanly close the connection by sending a close message and then
			// waiting (with timeout) for the server to close the connection.
			err := c.WriteMessage(websocket.CloseMessage, websocket.FormatCloseMessage(websocket.CloseNormalClosure, ""))
			if err != nil {
				logrus.Println("write close:", err)
				return
			}
			select {
			case <-done:
			case <-time.After(time.Second):
			}
			return
		}
	}
}
