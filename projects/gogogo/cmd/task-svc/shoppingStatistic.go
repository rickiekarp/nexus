package main

import (
	"fmt"
	"time"

	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/network"
	"rickiekarp.net/rickie/home/projects/gogogo/projects/gogogo/src/rickiekarp.net/parser/yamlparser"
)

func main() {

	// read config
	var config yamlparser.Requestdata
	config.GetConf()
	fmt.Println(config)

	curDate := time.Now().Format("01-2006")
	fmt.Println(curDate)

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
		Subject:   fmt.Sprintf("Shopping Statistic - %s", curDate),
		Message:   "Here is what you've spend last month.",
	}

	// Both calls below produce same result
	data.StructMethod()
	network.FuncPassStruct(data)

	network.Post("https://app.rickiekarp.net/HomeServer/statistics/shoppingValue", headerMap, &data)
}
