package main

import (
	"flag"
	"fmt"
	"net/url"
	"os"
	"os/signal"
	"syscall"
	"time"

	"git.rickiekarp.net/rickie/home/internal/project6/config"
	"git.rickiekarp.net/rickie/home/internal/project6/eventmanager"
	"git.rickiekarp.net/rickie/home/pkg/logger"
	"git.rickiekarp.net/rickie/home/pkg/network"
	"git.rickiekarp.net/rickie/home/pkg/sys"
	"git.rickiekarp.net/rickie/home/pkg/util"
	"github.com/gorilla/websocket"
	"github.com/sirupsen/logrus"
)

var host = flag.String("host", "localhost:12000", "target host")
var lockFile = flag.String("lock", "/tmp/project6svc.lock", "set the lock file to use")
var logFile = flag.String("log", "", "set the log file to use")

func init() {
	flag.Parse()

	// create exclusive lock on lock file, so program can only run once
	err := sys.GetExclusiveLock(*lockFile, 5, false)
	if err != nil {
		logrus.Error(err)
		os.Exit(1)
	}

	// read pid from lock file to print it out later
	pid, err := sys.GetLockFilePid(*lockFile)
	if err != nil {
		logrus.Error(err)
		logrus.Error("Can not get pid from lock file ", lockFile)
		os.Exit(1)
	}

	logger.SetupDefaultLogger(logFile)

	logrus.Info("Starting Project6 (Version: " + config.Version + ", Build: " + config.Build + ", PID: " + fmt.Sprint(pid) + ")")
}

func main() {

	interrupt := make(chan os.Signal, 1)
	signal.Notify(interrupt, os.Interrupt, syscall.SIGTERM, syscall.SIGINT)

	hostname, err := os.Hostname()
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}

	url := url.URL{Scheme: "ws", Host: *host, Path: "/ws"}
	logrus.Printf("connecting to %s (protocol: %s)", url.String(), hostname)

	webSockerDialer := websocket.DefaultDialer
	webSockerDialer.Subprotocols = []string{hostname}

	wsConn, _, err := webSockerDialer.Dial(url.String(), nil)
	if err != nil {
		logrus.Fatal("dial:", err)
	}
	defer wsConn.Close()

	done := make(chan struct{})

	go func() {
		defer close(done)
		for {
			_, message, err := wsConn.ReadMessage()
			if err != nil {
				logrus.Println("ReadMessageErr:", err)
				return
			}

			eventmanager.ProcessMessage(message)
		}
	}()

	ticker := time.NewTicker(1 * time.Minute)
	defer ticker.Stop()

	for {
		select {
		case <-done:
			return
		case <-ticker.C:
			if util.Exists("project6svc_update") {
				logrus.Info("Update file found! Stopping service")
				network.CloseWebSocket(wsConn)
				// waiting (with timeout) for the server to close the connection
				select {
				case <-done:
				case <-time.After(time.Second):
				}
				return
			}

		case <-interrupt:
			logrus.Println("signal received: interrupt")
			network.CloseWebSocket(wsConn)
			// waiting (with timeout) for the server to close the connection
			select {
			case <-done:
			case <-time.After(time.Second):
			}
			return
		}
	}
}
