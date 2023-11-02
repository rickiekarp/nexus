package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"git.rickiekarp.net/rickie/home/internal/nucleus/api"
	"git.rickiekarp.net/rickie/home/internal/nucleus/config"
	"git.rickiekarp.net/rickie/home/internal/nucleus/hub"
	globalConfig "git.rickiekarp.net/rickie/home/pkg/config"
	"git.rickiekarp.net/rickie/home/pkg/http"
	"github.com/sirupsen/logrus"
)

func init() {
	// set up program flags
	printHelp := flag.Bool("h", false, "print help")
	printVersion := flag.Bool("v", false, "print version")
	flag.Parse()

	if *printHelp {
		globalConfig.PrintUsage()
	}

	if *printVersion {
		fmt.Println(config.Version)
		os.Exit(0)
	}

	logrus.Info("Starting Nucleus (Version: " + config.Version + ", Build: " + config.Build + ")")
}

func main() {

	// load config files
	cfgError := config.ReadNucleusConfig()
	if cfgError != nil {
		logrus.Error("Could not load config!")
		os.Exit(1)
	}

	// Create channel for os.Signal notifications
	signals := make(chan os.Signal, 1)

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

	// set up nucleus
	hub.Nucleus = hub.NewHub()
	go hub.Nucleus.Run()

	apiServer := api.GetServer(config.NucleusConf.ServerAddr)
	logrus.Info("Starting API server on ", config.NucleusConf.ServerAddr)
	go http.StartApiServer(apiServer)

	// start monitoring
	go hub.CollectStats()

	// The program will wait here until it gets the expected signal
	logrus.Info("Started Nucleus, awaiting SIGINT or SIGTERM")

	<-applicationChannel

	logrus.Info("Stopping")
}
