package main

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/gorilla/mux"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/command"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/database"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/game/snakefx"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/network"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/parser/propertiesparser"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/parser/yamlparser"
)

const port = 8080

func main() {
	argsWithProg := os.Args
	fmt.Println(argsWithProg)

	command.ExecuteCommandAndPrintResult()
	returnCode := command.ExecuteCommandAndGetExitCode()
	fmt.Println(returnCode)

	// read config
	var config yamlparser.Requestdata
	config.GetConf()
	fmt.Println(config)

	network.Get()

	propertiesparser.Read("conf/config.properties")

	database.InitDB("mysql://user:pass@tcp(database:3306)/login")
	database.GetUsers()

	router := mux.NewRouter()

	router.HandleFunc("/highscore", snakefx.GetEmps).Methods("GET")
	router.HandleFunc("/highscore/{name}", snakefx.GetEmp).Methods("GET")
	router.HandleFunc("/highscore", snakefx.CreateEmp).Methods("POST")
	router.HandleFunc("/highscore/{name}", snakefx.DeleteEmp).Methods("DELETE")

	fmt.Println("Start listening on", port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", port), router))
}
