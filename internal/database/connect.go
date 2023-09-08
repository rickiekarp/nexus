package database

import (
	"database/sql"
	"fmt"

	"git.rickiekarp.net/rickie/home/internal/models"
	_ "github.com/go-sql-driver/mysql"
	"github.com/sirupsen/logrus"
)

var Databases []models.Database

var ConDataHome *sql.DB

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

func ConnectDataHome() {
	databaseConfig := GetConfigByDatabaseName("data_home")
	connection, err := GetConnection(databaseConfig.User, databaseConfig.Password, databaseConfig.Host, databaseConfig.Name)
	if err != nil {
		logrus.Warn(err)
	}
	ConDataHome = connection
}

func CheckDatabaseConnection() bool {
	if ConDataHome == nil {
		logrus.Warn("No connection to data_home exists, trying to connect again!")
		ConnectDataHome()
		if ConDataHome == nil {
			logrus.Warn("Can not open database connection! Is the database down?")
			return false
		}
	}
	return true
}
