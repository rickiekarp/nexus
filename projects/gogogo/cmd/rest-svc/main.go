package main

import (
	"fmt"
	"log"
	"net/http"

	"github.com/gorilla/mux"
	"rickiekarp.net/game/snakefx"
)

const port = 8080

func main() {
	router := mux.NewRouter()

	router.HandleFunc("/highscore", snakefx.GetEmps).Methods("GET")
	router.HandleFunc("/highscore/{name}", snakefx.GetEmp).Methods("GET")
	router.HandleFunc("/highscore", snakefx.CreateEmp).Methods("POST")
	router.HandleFunc("/highscore/{name}", snakefx.DeleteEmp).Methods("DELETE")

	fmt.Println("Start listening on", port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", port), router))
}
