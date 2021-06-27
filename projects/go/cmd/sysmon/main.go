package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"
	"time"

	"git.rickiekarp.net/rickie/home/projects/go/src/config"
	"git.rickiekarp.net/rickie/home/projects/go/src/util"
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
	// Create a channel for the line startup / heartbeat
	sysmonChannel := make(chan bool)

	// This goroutine executes a blocking receive for signals.
	// When it gets one it’ll print it out and then notify the program that it can finish.
	go func() {
		sig := <-signals
		logrus.Info("Signal received: ", sig)

		// indicate that the lineChannel can be closed and no further heartbeats should be send
		sysmonChannel <- true

		// indicate to quit the application
		applicationChannel <- true
	}()

	// start goroutine for anonymous function call
	// to keep the line up until SIGINT / SIGTERM
	go func() {
		for {
			// blocks until one of its cases can run
			select {
			// exit goroutine if lineChannel is closed
			case <-sysmonChannel:
				return
			// make sure this case is executed every 10 seconds
			// do not use a time.Sleep here since it causes a SIGKILL if the SIGTERM takes too long
			case <-time.After(10 * time.Second):
				result := util.ExecuteCmdAndGetOutput("/bin/sh", "-c", "/bin/ps")
				if result == "" {
					logrus.Error("command FAILED")
				}
				logrus.Info(result)
			}
		}
	}()

	// The program will wait here until it gets the expected signal
	logrus.Info("Started goroutines, awaiting SIGINT or SIGTERM")

	<-applicationChannel

	logrus.Info("Stopping")
}
