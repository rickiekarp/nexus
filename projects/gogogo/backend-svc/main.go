package main

import (
	"fmt"
	"os"

	"rickiekarp.net/database"
	"rickiekarp.net/http"
	"rickiekarp.net/parser"
)

func main() {
	argsWithProg := os.Args
	fmt.Println(argsWithProg)

	parser.Read("conf/config.properties")

	http.Get()

	database.GetUsers()

}
