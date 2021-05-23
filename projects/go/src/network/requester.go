package network

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"

	"git.rickiekarp.net/rickie/home/projects/go/src/common/utils"
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

var (
	outfile, err = utils.CheckFile("/var/log/pi/today/requester.log")
	logger       = log.New(outfile, "", log.LstdFlags|log.Lshortfile)
)

// Method on struct type
func (class RequestData) StructMethod() {
	logger.Println(class.Recipient)
}

// Function that takes struct type as the parameter
func FuncPassStruct(class RequestData) {
	logger.Println(class.Recipient)
}

func Post(url string, headers map[string]string, class *RequestData) {

	data, _ := json.Marshal(&class)

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(data))
	if err != nil {
		logger.Println(err)
	}

	for k, v := range headers {
		logger.Printf("key[%s] value[%s]\n", k, v)
		req.Header.Set(k, v)
	}

	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		logger.Fatalln(err)
	}
	defer resp.Body.Close()
}

func Get() {
	resp, err := http.Get("https://rickiekarp.net")
	if err != nil {
		logger.Fatalln(err)
	}

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		logger.Fatalln(err)
	}

	logger.Println(string(body))
}
