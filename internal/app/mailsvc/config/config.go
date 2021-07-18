package config

import (
	"io/ioutil"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

type MailConf struct {
	Mail struct {
		Host     string `yaml:"host"`
		Port     int    `yaml:"port"`
		Username string `yaml:"username"`
		Password string `yaml:"password"`
	} `yaml:"mail"`
}

var MailConfig MailConf

// ReadSysmonConfig reads the given config file and tries to unmarshal it into the given configStruct
func ReadMailConfig(configDir string) error {

	// read configfile
	yamlFile, err := ioutil.ReadFile(configDir + "config.yml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &MailConfig)

	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	return nil
}
