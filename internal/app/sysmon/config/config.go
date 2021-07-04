package config

import (
	"io/ioutil"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

type SysmonConfig struct {
	Graphite struct {
		Host string `json:"host"`
		Port int    `json:"port"`
	}
}

var SysmonConf SysmonConfig

// ReadSysmonConfig reads the given config file and tries to unmarshal it into the given configStruct
func ReadSysmonConfig() error {

	// read configfile
	yamlFile, err := ioutil.ReadFile("config/sysmon/config.yaml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &SysmonConf)

	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	return nil
}
