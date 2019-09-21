package parser

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strings"
)

type Config map[string]string

func Read(filename string) {

	test()

	config, err := readConfig(filename)
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	fmt.Println("Read Config:", config)
	// assign values from config file to variables
	url := config["url"]
	user := config["user"]
	issuetype := config["pass"]

	fmt.Println("url :", url)
	fmt.Println("user :", user)
	fmt.Println("pass :", issuetype)
}

func readConfig(filename string) (Config, error) {
	// init with some bogus data
	config := Config{
		"port": "1111",
		"pass": "22222",
		"ip":   "127.0.0.1",
	}
	if len(filename) == 0 {
		return config, nil
	}
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	reader := bufio.NewReader(file)

	for {
		line, err := reader.ReadString('\n')

		// check if the line has = sign
		// and process the line. Ignore the rest.
		if equal := strings.Index(line, "="); equal >= 0 {
			if key := strings.TrimSpace(line[:equal]); len(key) > 0 {
				value := ""
				if len(line) > equal {
					value = strings.TrimSpace(line[equal+1:])
				}
				// assign the config map
				config[key] = value
			}
		}
		if err == io.EOF {
			break
		}
		if err != nil {
			return nil, err
		}
	}
	return config, nil
}

func Hello() string {
	return "Hello, world."
}

func test() {
	i := 7
	inc(&i)
	fmt.Println(i)
}

func inc(x *int) {
	*x++
}
