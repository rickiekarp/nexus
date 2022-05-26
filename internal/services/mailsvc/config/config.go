package config

import (
	"io/ioutil"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var ConfigBaseDir = "projects/module-deployment/values/services/mailsvc/dev/config/" // ConfigBaseDir set during go build using ldflags

type MailConf struct {
	ServerAddr string `yaml:"serverAddr"`
	Mail       struct {
		Host     string `yaml:"host"`
		Port     int    `yaml:"port"`
		Username string `yaml:"username"`
		Password string `yaml:"password"`
	} `yaml:"mail"`
	Databases []Database `yaml:"databases"`
}
type Database struct {
	Name             string `yaml:"name"`
	User             string `yaml:"user"`
	Password         string `yaml:"password"`
	Host             string `yaml:"host"`
	AdditionalParams string `yaml:"additionalParams"`
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

// GetConfigByDatabaseName iterates over all databases in the config and returns a
// pointer to the config entry for the given databaseName string
func GetConfigByDatabaseName(databaseName string) *Database {
	for _, databaseCfg := range MailConfig.Databases {
		if databaseCfg.Name == databaseName {
			return &databaseCfg
		}
	}
	return nil
}
