package channel

import (
	"fmt"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/app/sysmon/utils"
	"git.rickiekarp.net/rickie/home/pkg/apm"
	"git.rickiekarp.net/rickie/home/pkg/util"
	"github.com/sirupsen/logrus"
)

var (
	SysmonChannel chan bool = make(chan bool)
)

func Schedule() {
	for {
		// blocks until one of its cases can run
		select {
		// exit goroutine if sysmonChannel is closed
		case <-SysmonChannel:
			fmt.Println("stopping sysmon")
			return
		// make sure this case is executed every 10 seconds
		// do not use a time.Sleep here since it causes a SIGKILL if the SIGTERM takes too long
		case <-time.After(5 * time.Second):
			result, err := util.ExecuteCmdAndGetOutput("/bin/ps")
			if err != nil {
				logrus.Error("command FAILED ", err)
			}
			logrus.Info(string(result))
			apm.PrintMemUsage()
		}
	}
}

func RestartLineEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called RestartLineEndpoint")
	w.WriteHeader(200)
	w.Write([]byte("RestartLineEndpoint Response"))

	if !utils.IsChannelOpen(SysmonChannel) {
		logrus.Info("channel is already closed, ignoring")
		return
	}

	close(SysmonChannel)
}

func StartEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called startEndpoint")
	w.WriteHeader(200)
	w.Write([]byte("startEndpoint Response"))

	if utils.IsChannelOpen(SysmonChannel) {
		logrus.Info("channel open already")
		return
	}

	SysmonChannel = make(chan bool)
	go Schedule()
}

func StatusEndpoint(w http.ResponseWriter, r *http.Request) {
	logrus.Print("called statusEndpoint")
	w.WriteHeader(200)

	if utils.IsChannelOpen(SysmonChannel) {
		w.Write([]byte("true"))
	} else {
		w.Write([]byte("false"))
	}
}
