package main

import (
	"flag"
	"fmt"
	"os"
	"os/signal"
	"syscall"

	"git.rickiekarp.net/rickie/home/internal/nexus/api"
	"git.rickiekarp.net/rickie/home/internal/nexus/blockchain"
	"git.rickiekarp.net/rickie/home/internal/nexus/channel"
	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub"
	"git.rickiekarp.net/rickie/home/internal/nexus/hub/hubqueue"
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

	logrus.Info("Starting Nexus (Version: " + config.Version + ", Build: " + config.Build + ")")
}

func main() {

	// load config files
	cfgError := config.ReadNexusConfig()
	if cfgError != nil {
		logrus.Error("Could not load config!")
		os.Exit(1)
	}

	config.ReadSysTempConfig()

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

	// set up nexus
	hub.Nexus = hub.NewHub()
	go hub.Nexus.Run()

	if config.NexusConf.NexusChain.Enabled {
		blockchain.ValidateStorage()
		blockchain.InitNetwork()
	}

	apiServer := api.GetServer(config.NexusConf.ServerAddr)

	logrus.Info("Starting service: HubQueue")
	go hubqueue.StartHubQueue()

	logrus.Info("Starting service: API server on ", config.NexusConf.ServerAddr)
	go http.StartApiServer(apiServer)

	logrus.Info("Starting service: MetricsCollector")
	go hub.StartMetricsCollector()

	logrus.Info("Checking additonal service availability")
	go channel.ScheduleWeatherUpdate()

	// The program will wait here until it gets the expected signal
	logrus.Info("Started Nexus, awaiting SIGINT or SIGTERM")

	<-applicationChannel

	logrus.Info("Stopping")
}
