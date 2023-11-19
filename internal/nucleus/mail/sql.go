package mail

import (
	"database/sql"
	"encoding/json"
	"log"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

type NotificationToken struct {
	Name     string `json:"name"`
	Token    string `json:"token"`
	Template string `json:"template"`
}

type ReminderData struct {
	Id                                                                                        int
	users_id, dateAdded, Description, reminder_interval, reminder_day, isDeleted, lastUpdated string
	reminder_startdate, reminder_senddate, reminder_enddate                                   sql.NullString
}

const GET_APPLICATION_SETTINGS_NOTIFICATIONTOKEN_CONTENT = "SELECT content FROM applicationsettings where identifier = 'notificationtoken'"

const SELECT_REMINDER_LIST = `select * from tracking_todo 
where users_id = ? 
AND dayofweek(curdate())-1 = reminder_day 
OR (reminder_senddate IS NULL OR date(now()) >= date(reminder_senddate) + interval reminder_interval day) 
AND reminder_enddate > now() 
AND isDeleted = false`

const UPDATE_REMINDER_SENDDATE = "update tracking_todo set reminder_senddate = now(), lastUpdated = now() where id = ?"

func GetActiveRemindersForUser(userId int) *[]ReminderData {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConDataHome) {
		return nil
	}

	rows, err := database.ConDataHome.Connection.Query(SELECT_REMINDER_LIST, userId)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var reminders []ReminderData
	for rows.Next() {
		var data ReminderData
		if err := rows.Scan(
			&data.Id,
			&data.users_id,
			&data.dateAdded,
			&data.Description,
			&data.reminder_interval,
			&data.reminder_day,
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

func SetReminderSendDateForReminderId(reminderId int) error {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConDataHome) {
		return nil
	}

	_, err := database.ConDataHome.Connection.Exec(UPDATE_REMINDER_SENDDATE, reminderId)
	if err != nil {
		logrus.Error(err)
		return err
	}
	return nil
}

func getApplicationSettingsNotificationTokenContent() *[]NotificationToken {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConDataHome) {
		return nil
	}

	rows, err := database.ConDataHome.Connection.Query(GET_APPLICATION_SETTINGS_NOTIFICATIONTOKEN_CONTENT)
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
