package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
	"os"

	"git.rickiekarp.net/rickie/home/internal/database"
	"git.rickiekarp.net/rickie/home/internal/parser/yamlparser"
	"git.rickiekarp.net/rickie/home/services/gameserver/api"
	"github.com/gorilla/mux"
)

var Version = "development" // Version set during go build using ldflags

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

	log.Println(config)
	log.Println("tail:", args)

	DB, err := database.GetConnection(config.DB.Username, config.DB.Password, config.DB.Url, config.DB.Database)
	if err != nil {
		log.Println(err)
	}
	api.DB = DB

	router := mux.NewRouter()

	router.HandleFunc("/gamedata/ranking/highscore", api.GetEmps).Methods("GET")
	router.HandleFunc("/gamedata/ranking/highscore/{name}", api.GetEmp).Methods("GET")
	router.HandleFunc("/gamedata/ranking/addHighscore", api.CreateEmp).Methods("POST")
	router.HandleFunc("/gamedata/ranking/highscore/{name}", api.DeleteEmp).Methods("DELETE")

	log.Println("Start listening on", *port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", *port), router))
}

// Test:
// curl -d '{"name":"Test", "points":"1"}' -H "Content-Type: application/json" -X POST http://localhost:8080/gamedata/addHighscore
