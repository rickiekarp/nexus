package database

import (
	"database/sql"
	"fmt"

	"git.rickiekarp.net/rickie/home/pkg/models"
	_ "github.com/go-sql-driver/mysql"
	"github.com/sirupsen/logrus"
)

var Databases []models.Database

var ConLogin models.DatabaseConnection
var ConDataHome models.DatabaseConnection

func GetConnection(username, password, url, database string) (*sql.DB, error) {
	con, err := sql.Open("mysql", fmt.Sprintf(
		"%s:%s@tcp(%s)/%s",
		username,
		password,
		url,
		database,
	))
	if err != nil {
		return nil, err
	}

	if err = con.Ping(); err != nil {
		return nil, err
	}

	return con, err
}

// GetConfigByDatabaseName iterates over all databases in the config and returns a
// pointer to the config entry for the given databaseName string
func GetConfigByDatabaseName(databaseName string) *models.Database {
	for _, databaseCfg := range Databases {
		if databaseCfg.Name == databaseName {
			return &databaseCfg
		}
	}
	return nil
}

func ConnectDatabase(database models.DatabaseConnection) bool {
	databaseConfig := GetConfigByDatabaseName(database.Name)
	if databaseConfig == nil {
		logrus.Error("No database connection config found, is it missing from your config?")
		return false
	}
	connection, err := GetConnection(databaseConfig.User, databaseConfig.Password, databaseConfig.Host, databaseConfig.Name)
	if err != nil {
		logrus.Error(err)
		return false
	}

	switch database.Name {
	case "nexus":
		ConDataHome.Connection = connection
	case "login":
		ConLogin.Connection = connection
	}
	return true
}

func CheckDatabaseConnection(databaseConnection models.DatabaseConnection) bool {
	if databaseConnection.Connection == nil {
		logrus.Warn("No connection to " + databaseConnection.Name + " exists, trying to connect again!")
		if !ConnectDatabase(databaseConnection) {
			logrus.Error("Can not open database connection! Is the database down?")
			return false
		}
	}
	return true
}
