package http

import (
	"io/ioutil"
	"log"
	"net/http"
)

var Morning = "hello"

func Get() {
	resp, err := http.Get("https://rickiekarp.net")
	if err != nil {
		log.Fatalln(err)
	}

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		log.Fatalln(err)
	}

	log.Println(string(body))
}
