package main

import (
	"fmt"

	"rickiekarp.net/network"
	"rickiekarp.net/parser/yamlparser"
)

func main() {

	// read config
	var config yamlparser.Requestdata
	config.GetConf()
	fmt.Println(config)

	//prepare request
	headerMap := map[string]string{
		"Authorization":        fmt.Sprintf("Basic %s", config.Authorization),
		"X-Notification-Token": config.Token,
		"X-UserId":             config.Xuserid,
		"X-Days":               config.Xdays,
		"Content-Type":         "application/json",
	}

	data := network.RequestData{
		Recipient: config.Recipient,
		Subject:   "subject",
		Message:   "message",
	}

	// Both calls below produce same result
	data.StructMethod()
	network.FuncPassStruct(data)

	network.Post("https://app.rickiekarp.net/HomeServer/statistics/shoppingValue", headerMap, &data)
}
