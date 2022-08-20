package config

import (
	"io/ioutil"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var UptimeMonitoringConf UptimeConf

type UptimeConf struct {
	Enabled        bool    `json:"enabled"`
	GraphitePrefix string  `json:"graphiteprefix"`
	NotifyApiUrl   string  `json:"notifyapiurl"`
	AlertThreshold float64 `json:"alertthreshold"`
}

// ReadWeatherConfig reads the given config file
func ReadUptimeMonitoringConfig() error {

	// read configfile
	yamlFile, err := ioutil.ReadFile(ConfigBaseDir + "uptime.yaml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &UptimeMonitoringConf)

	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	return nil
}
