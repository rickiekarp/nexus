package config

import (
	"os"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var SysTemperatureConf SysTempConf

type SysTempConf struct {
	Enabled              bool    `json:"enabled"`
	NotifyEmailRecipient string  `json:"notifyemailrecipient"`
	NotifyApiUrl         string  `json:"notifyapiurl"`
	AlertThreshold       float64 `json:"alertthreshold"`
	NotificationToken    string  `json:"notificationtoken"`
}

// ReadWeatherConfig reads the given config file
func ReadSysTempConfig() error {

	// read configfile
	yamlFile, err := os.ReadFile(ConfigBaseDir + "systemp.yaml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &SysTemperatureConf)

	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	return nil
}
