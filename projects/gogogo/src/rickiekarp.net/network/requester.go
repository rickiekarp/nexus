package network

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
)

type RequestData struct {
	Recipient string         `json:"to"`
	Subject   string         `json:"subject"`
	Message   string         `json:"message"`
	Data      AdditionalData `json:"additionalData"`
}

type AdditionalData struct {
	Date string `json:"date"`
	Tags string `json:"tags"`
	URL  string `json:"url"`
}

// Method on struct type
func (class RequestData) StructMethod() {
	log.Println(class.Recipient)
}

// Function that takes struct type as the parameter
func FuncPassStruct(class RequestData) {
	log.Println(class.Recipient)
}

func Post(url string, headers map[string]string, class *RequestData) {

	data, _ := json.Marshal(&class)

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(data))
	if err != nil {
		log.Println(err)
	}

	for k, v := range headers {
		log.Printf("key[%s] value[%s]\n", k, v)
		req.Header.Set(k, v)
	}

	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		log.Fatalln(err)
	}
	defer resp.Body.Close()
}

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
