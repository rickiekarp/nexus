package main

import (
	"fmt"
	"os"

	"rickiekarp.net/command"
	"rickiekarp.net/database"
	"rickiekarp.net/http"
	"rickiekarp.net/parser"
)

func main() {
	argsWithProg := os.Args
	fmt.Println(argsWithProg)

	command.ExecuteCommandAndPrintResult()

	returnCode := command.ExecuteCommandAndGetExitCode()
	fmt.Println(returnCode)

	parser.Read("conf/config.properties")

	http.Get()

	database.GetUsers()

}
