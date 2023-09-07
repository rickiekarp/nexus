package models

type Database struct {
	Name             string `yaml:"name"`
	User             string `yaml:"user"`
	Password         string `yaml:"password"`
	Host             string `yaml:"host"`
	AdditionalParams string `yaml:"additionalParams"`
}
