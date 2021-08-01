package datasource

import (
	"database/sql"
	"encoding/json"
	"log"

	"git.rickiekarp.net/rickie/home/internal/app/mailsvc/config"
	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

var ConDataHome *sql.DB

type NotificationToken struct {
	Name     string `json:"name"`
	Token    string `json:"token"`
	Template string `json:"template"`
}

const GET_APPLICATION_SETTINGS_NOTIFICATIONTOKEN_CONTENT = "SELECT content FROM applicationsettings where identifier = 'notificationtoken'"

func ConnectDataHome() {
	databaseConfig := config.GetConfigByDatabaseName("data_home")
	connection, err := database.GetConnection(databaseConfig.User, databaseConfig.Password, databaseConfig.Host, databaseConfig.Name)
	if err != nil {
		log.Println(err)
	}
	ConDataHome = connection
}

func GetApplicationSettingsNotificationTokenContent() *[]NotificationToken {
	// connection not available
	if ConDataHome == nil {
		return nil
	}

	rows, err := ConDataHome.Query(GET_APPLICATION_SETTINGS_NOTIFICATIONTOKEN_CONTENT)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var content string
	rows.Next()
	err = rows.Scan(&content)
	if err != nil {
		logrus.Error(err)
		return nil
	}

	// unmarshal db content into slice
	var notificationTokenData []NotificationToken
	_ = json.Unmarshal([]byte(content), &notificationTokenData)
	return &notificationTokenData
}
