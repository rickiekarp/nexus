package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"git.rickiekarp.net/rickie/home/internal/config"
	"git.rickiekarp.net/rickie/home/internal/http"
	"git.rickiekarp.net/rickie/home/internal/services/mailsvc/api"
	mailconfig "git.rickiekarp.net/rickie/home/internal/services/mailsvc/config"
	"git.rickiekarp.net/rickie/home/internal/services/mailsvc/datasource"
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

	logrus.Info("Starting mail service (" + Version + ")")
}

func main() {

	logrus.Info("Loading mail config")
	err := mailconfig.ReadMailConfig(mailconfig.ConfigBaseDir)
	if err != nil {
		logrus.Error("Could not load mail config!")
		os.Exit(1)
	}

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

		// indicate to quit the application
		applicationChannel <- true
	}()

	// open connection to data_home database
	datasource.ConnectDataHome()

	apiServer := api.GetMailServer(mailconfig.MailConfig.ServerAddr)
	logrus.Info("Starting API server on ", mailconfig.MailConfig.ServerAddr)
	go http.StartApiServer(apiServer)

	// The program will wait here until it gets the expected signal
	logrus.Info("Started goroutines, awaiting SIGINT or SIGTERM")

	<-applicationChannel

	logrus.Info("Stopping mail service")
}
