package main

import (
	"fmt"
	"io/ioutil"
	"log"

	"gopkg.in/yaml.v2"
	"rickiekarp.net/http"
)

type conf struct {
	Authorization string `yaml:"authorization"`
	Token         string `yaml:"token"`
	Xuserid       string `yaml:"xuserid"`
	Xdays         string `yaml:"xdays"`
	Recipient     string `yaml:"recipient"`
}

func (c *conf) getConf() *conf {

	yamlFile, err := ioutil.ReadFile("conf/conf.yaml")
	if err != nil {
		log.Printf("yamlFile.Get err   #%v ", err)
	}
	err = yaml.Unmarshal(yamlFile, c)
	if err != nil {
		log.Fatalf("Unmarshal: %v", err)
	}

	return c
}

func main() {

	// read config
	var c conf
	c.getConf()
	fmt.Println(c)

	//prepare request
	headerMap := map[string]string{
		"Authorization":        fmt.Sprintf("Basic %s", c.Authorization),
		"X-Notification-Token": c.Token,
		"X-UserId":             c.Xuserid,
		"X-Days":               c.Xdays,
		"Content-Type":         "application/json",
	}

	data := http.RequestData{
		Recipient: c.Recipient,
		Subject:   "subject",
		Message:   "message",
	}

	// Both calls below produce same result
	data.StructMethod()
	http.FuncPassStruct(data)

	http.Post("https://app.rickiekarp.net/HomeServer/statistics/shoppingValue", headerMap, &data)
}
