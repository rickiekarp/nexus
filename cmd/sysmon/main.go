package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"git.rickiekarp.net/rickie/home/internal/app/sysmon/api"
	"git.rickiekarp.net/rickie/home/internal/app/sysmon/channel"
	"git.rickiekarp.net/rickie/home/internal/app/sysmon/utils"
	"git.rickiekarp.net/rickie/home/pkg/config"
	"github.com/sirupsen/logrus"
)

var (
	Version = "development" // Version set during go build using ldflags
)

func init() {
	printHelp := flag.Bool("h", false, "print help")
	printVersion := flag.Bool("v", false, "print version")
	flag.Parse()

	if *printHelp {
		config.PrintUsage()
	}

	if *printVersion {
		fmt.Println(Version)
		os.Exit(0)
	}
}

func main() {

	// Create channel for os.Signal notifications
	signals := make(chan os.Signal)

	// signal.Notify registers the given channel to receive notifications of the specified signals.
	signal.Notify(signals, syscall.SIGINT, syscall.SIGTERM)

	// Create a channel to receive these notifications (we’ll also make one to notify us when the program can exit).
	applicationChannel := make(chan bool)

	// This goroutine executes a blocking receive for signals.
	// When it gets one it’ll print it out and then notify the program that it can finish.
	go func() {
		sig := <-signals
		logrus.Info("Signal received: ", sig)

		// indicate that the sysmonChannel can be closed and no further heartbeats should be send
		if utils.IsChannelOpen(channel.SysmonChannel) {
			channel.SysmonChannel <- true
		}

		// indicate to quit the application
		applicationChannel <- true
	}()

	// start goroutine for anonymous function call
	// to keep the line up until SIGINT / SIGTERM
	go channel.Schedule()

	apiServer := api.GetServer(":8080")
	go api.StartApiServer(apiServer)

	// The program will wait here until it gets the expected signal
	logrus.Info("Started goroutines, awaiting SIGINT or SIGTERM")

	<-applicationChannel

	logrus.Info("Stopping")
}
