package main

import (
	"fmt"
	"log"
	"net/http"
	"time"

	"git.rickiekarp.net/rickie/home/internal/app/blockchain"
	"github.com/gorilla/mux"
)

// https://github.com/nosequeldeebee

func main() {
	blockchain.InitBlockChain()

	run()
}

func run() error {
	mux := makeMuxRouter()
	httpAddr := ":8080"
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

func makeMuxRouter() http.Handler {
	muxRouter := mux.NewRouter()
	muxRouter.HandleFunc("/", handleGetBlockchain).Methods("GET")
	return muxRouter
}

func handleGetBlockchain(w http.ResponseWriter, r *http.Request) {
	fmt.Println("asd")
}
