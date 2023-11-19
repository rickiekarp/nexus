package config

import (
	"io/ioutil"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"git.rickiekarp.net/rickie/home/pkg/models"
	"git.rickiekarp.net/rickie/home/pkg/monitoring/graphite"
	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var (
	Version          = "1"                                                        // Version set during go build using ldflags
	Build            = "development"                                              // Build set during go build using ldflags
	ConfigBaseDir    = "deployments/module-deployment/values/nucleus/dev/config/" // ConfigBaseDir set during go build using ldflags
	ResourcesBaseDir = "web/nucleus/"
)

type NucleusConfig struct {
	ServerAddr string `yaml:"serverAddr"`
	Graphite   struct {
		Enabled bool   `yaml:"enabled"`
		Host    string `yaml:"host"`
		Port    int    `yaml:"port"`
	}
	Mail struct {
		Host     string `yaml:"host"`
		Port     int    `yaml:"port"`
		Username string `yaml:"username"`
		Password string `yaml:"password"`
		Notify   struct {
			Recipient string `yaml:"recipient"`
		} `yaml:"notify"`
	} `yaml:"mail"`
	Databases []models.Database `yaml:"databases"`
	Project6  struct {
		MinClientVersion string `yaml:"minClientVersion"`
	}
}

var NucleusConf NucleusConfig

// ReadNucleusConfig reads the given config file and tries to unmarshal it into the given configStruct
func ReadNucleusConfig() error {

	// read configfile
	yamlFile, err := ioutil.ReadFile(ConfigBaseDir + "config.yaml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &NucleusConf)
	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	// set graphite target config
	graphite.Host = NucleusConf.Graphite.Host
	graphite.Port = NucleusConf.Graphite.Port

	database.Databases = NucleusConf.Databases
	database.ConLogin = models.DatabaseConnection{Name: "login"}
	database.ConDataHome = models.DatabaseConnection{Name: "data_home"}

	return nil
}
