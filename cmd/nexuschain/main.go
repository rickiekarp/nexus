package main

import (
	"log"
	"net/http"
	"os"
	"time"

	"git.rickiekarp.net/rickie/home/internal/nexuschain/blockchain"
	"github.com/sirupsen/logrus"
)

var httpAddr = ":8080"

func main() {
	run()
}

func run() error {

	err := blockchain.LoadConfig()
	if err != nil {
		logrus.Error("Could not load chain config!")
		os.Exit(1)
	}
	logrus.Info(blockchain.ChainConfig)

	blockchain.ValidateStorage()

	blockchain.InitNetwork()

	mux := blockchain.MakeMuxRouter()
	log.Println("Listening on ", httpAddr)
	s := &http.Server{
		Addr:           httpAddr,
		Handler:        mux,
		ReadTimeout:    10 * time.Second,
		WriteTimeout:   10 * time.Second,
		MaxHeaderBytes: 1 << 20,
	}

	if err := s.ListenAndServe(); err != nil {
		return err
	}
	return nil
}
