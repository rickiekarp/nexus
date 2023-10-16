package network

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"net/http"

	"github.com/sirupsen/logrus"
)

func Post(url string, headers map[string]string, class interface{}) error {

	data, _ := json.Marshal(&class)

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(data))
	if err != nil {
		logrus.Error(err)
		return err
	}

	for k, v := range headers {
		logrus.Printf("key[%s] value[%s]\n", k, v)
		req.Header.Set(k, v)
	}

	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		logrus.Error(err)
		return err
	}
	defer resp.Body.Close()
	return nil
}

func Get(url string) {
	resp, err := http.Get(url)
	if err != nil {
		logrus.Error(err)
	}

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		logrus.Error(err)
	}

	logrus.Println(string(body))
}
