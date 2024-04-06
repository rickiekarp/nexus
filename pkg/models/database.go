package models

import "database/sql"

type Database struct {
	Name     string `yaml:"name"`
	User     string `yaml:"user"`
	Password string `yaml:"password"`
	Host     string `yaml:"host"`
}

type DatabaseConnection struct {
	Name       string
	Connection *sql.DB
}
