package config

import (
	"os"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"git.rickiekarp.net/rickie/home/pkg/integrations/graphite"
	"git.rickiekarp.net/rickie/home/pkg/models"
	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var (
	Version          = "1"                                                      // Version set during go build using ldflags
	Build            = "development"                                            // Build set during go build using ldflags
	ConfigBaseDir    = "deployments/module-deployment/values/nexus/dev/config/" // ConfigBaseDir set during go build using ldflags
	ResourcesBaseDir = "web/nexus/"
	Hostname         string
)

type NexusConfig struct {
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
	NexusChain struct {
		Enabled  bool `yaml:"enabled"`
		Defaults struct {
			StakeReward int64 `yaml:"stakeReward"`
		}
		Storage struct {
			Type string `yaml:"type"`
			Path string `yaml:"path"`
		}
	}
}

var NexusConf NexusConfig

// ReadNexusConfig reads the given config file and tries to unmarshal it into the given configStruct
func ReadNexusConfig() error {

	hostname, err := os.Hostname()
	if err != nil {
		logrus.Error("Could not determine hostname! ", err)
		return err
	}
	Hostname = hostname

	// read configfile
	yamlFile, err := os.ReadFile(ConfigBaseDir + "config.yaml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &NexusConf)
	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	// set graphite target config
	graphite.Host = NexusConf.Graphite.Host
	graphite.Port = NexusConf.Graphite.Port

	database.Databases = NexusConf.Databases
	database.ConLogin = models.DatabaseConnection{Name: "login"}
	database.ConDataHome = models.DatabaseConnection{Name: "data_home"}

	return nil
}
