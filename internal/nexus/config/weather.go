package config

import (
	"os"

	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

var WeatherConf WeatherApiConf

type WeatherApiConf struct {
	Enabled        bool     `json:"enabled"`
	GraphitePrefix string   `json:"graphiteprefix"`
	ApiKey         string   `json:"apikey"`
	CityIds        []string `json:"cityids"`
	Interval       int      `json:"interval"`
}

// ReadWeatherConfig reads the given config file
func ReadWeatherConfig() error {

	// read configfile
	yamlFile, err := os.ReadFile(ConfigBaseDir + "weather.yaml")
	if err != nil {
		logrus.Error("yamlFile.Get err: ", err)
		return err
	}

	// unmarshal config file depending on given configStruct
	err = yaml.Unmarshal(yamlFile, &WeatherConf)

	if err != nil {
		logrus.Error("Unmarshal failed: ", err)
		return err
	}

	return nil
}
