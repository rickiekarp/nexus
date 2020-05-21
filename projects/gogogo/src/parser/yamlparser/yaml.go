package yamlparser

import (
	"io/ioutil"
	"log"

	"gopkg.in/yaml.v2"
)

type Requestdata struct {
	Authorization string `yaml:"authorization"`
	Token         string `yaml:"token"`
	Xuserid       string `yaml:"xuserid"`
	Xdays         string `yaml:"xdays"`
	Recipient     string `yaml:"recipient"`
	Gamesrv       struct {
		Port int `yaml: port`
	}
	DB struct {
		Username string `yaml:"user"`
		Password string `yaml:"password"`
		Url      string `yaml:"url"`
		Database string `yaml:"database"`
	}
}

func (c *Requestdata) GetConf() *Requestdata {

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
