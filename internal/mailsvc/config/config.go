package config

import (
	"io/ioutil"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"git.rickiekarp.net/rickie/home/pkg/models"
	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var ConfigBaseDir = "deployments/module-deployment/values/mailsvc/dev/config/" // ConfigBaseDir set during go build using ldflags

type MailConf struct {
	ServerAddr string `yaml:"serverAddr"`
	Mail       struct {
		Host     string `yaml:"host"`
		Port     int    `yaml:"port"`
		Username string `yaml:"username"`
		Password string `yaml:"password"`
	} `yaml:"mail"`
	Databases []models.Database `yaml:"databases"`
	Notify    struct {
		Recipient string `yaml:"recipient"`
	} `yaml:"notify"`
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

	database.Databases = MailConfig.Databases
	database.ConDataHome = models.DatabaseConnection{Name: "data_home"}

	return nil
}
