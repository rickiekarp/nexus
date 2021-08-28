package datasource

import (
	"database/sql"
	"encoding/json"
	"log"

	"github.com/sirupsen/logrus"
)

var ConDataHome *sql.DB

type NotificationToken struct {
	Name     string `json:"name"`
	Token    string `json:"token"`
	Template string `json:"template"`
}

type ReminderData struct {
	id, users_id, dateAdded, Description, reminder_interval, isDeleted, lastUpdated string
	reminder_startdate, reminder_senddate, reminder_enddate                         sql.NullString
}

const GET_APPLICATION_SETTINGS_NOTIFICATIONTOKEN_CONTENT = "SELECT content FROM applicationsettings where identifier = 'notificationtoken'"

const SELECT_REMINDER_LIST = "select * from tracking_todo where users_id = ? AND reminder_startdate < now() AND (reminder_enddate IS NULL OR reminder_enddate > now())"

func GetActiveRemindersForUser(userId int) *[]ReminderData {
	// check if the database is available
	if !checkDatabaseConnection() {
		return nil
	}

	rows, err := ConDataHome.Query(SELECT_REMINDER_LIST, userId)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var reminders []ReminderData
	for rows.Next() {
		var data ReminderData
		if err := rows.Scan(
			&data.id,
			&data.users_id,
			&data.dateAdded,
			&data.Description,
			&data.reminder_interval,
			&data.reminder_startdate,
			&data.reminder_senddate,
			&data.reminder_enddate,
			&data.isDeleted,
			&data.lastUpdated,
		); err != nil {
			log.Fatal(err)
		}
		reminders = append(reminders, data)
	}

	if err := rows.Err(); err != nil {
		log.Fatal(err)
	}

	return &reminders

}

func getApplicationSettingsNotificationTokenContent() *[]NotificationToken {
	// check if the database is available
	if !checkDatabaseConnection() {
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

func CheckNotificationTokenExistence(tokenToFind string) bool {
	tokenData := getApplicationSettingsNotificationTokenContent()
	if tokenData != nil {
		for _, elem := range *tokenData {
			if elem.Token == tokenToFind {
				return true
			}
		}
	}
	return false
}
