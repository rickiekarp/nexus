package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
	"os"

	"git.rickiekarp.net/rickie/home/projects/go/api"
	"git.rickiekarp.net/rickie/home/projects/go/src/database"
	"git.rickiekarp.net/rickie/home/projects/go/src/parser/yamlparser"
	"github.com/gorilla/mux"
)

// Version set during go build using ldflags
var Version = "development"

func main() {

	// read config
	var config yamlparser.Requestdata
	config.GetConf()

	port := flag.Int("port", config.Gamesrv.Port, "application port")
	flag.Parse()

	args := flag.Args()
	if len(args) > 0 {
		switch args[0] {
		case "version":
			fmt.Println(Version)
			os.Exit(0)
		}
	}

	fmt.Println(config)
	fmt.Println("tail:", args)

	database.GetConnection(config.DB.Username, config.DB.Password, config.DB.Url, config.DB.Database)

	router := mux.NewRouter()

	router.HandleFunc("/gamedata/ranking/highscore", api.GetEmps).Methods("GET")
	router.HandleFunc("/gamedata/ranking/highscore/{name}", api.GetEmp).Methods("GET")
	router.HandleFunc("/gamedata/ranking/addHighscore", api.CreateEmp).Methods("POST")
	router.HandleFunc("/gamedata/ranking/highscore/{name}", api.DeleteEmp).Methods("DELETE")

	fmt.Println("Start listening on", *port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", *port), router))
}

// Test:
// curl -d '{"name":"Test", "points":"1"}' -H "Content-Type: application/json" -X POST http://localhost:8080/gamedata/addHighscore
