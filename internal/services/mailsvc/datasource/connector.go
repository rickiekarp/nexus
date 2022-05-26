package datasource

import (
	"git.rickiekarp.net/rickie/home/internal/database"
	"git.rickiekarp.net/rickie/home/internal/services/mailsvc/config"
	"github.com/sirupsen/logrus"
)

func ConnectDataHome() {
	databaseConfig := config.GetConfigByDatabaseName("data_home")
	connection, err := database.GetConnection(databaseConfig.User, databaseConfig.Password, databaseConfig.Host, databaseConfig.Name)
	if err != nil {
		logrus.Warn(err)
	}
	ConDataHome = connection
}

func checkDatabaseConnection() bool {
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
