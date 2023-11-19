package webpage

import (
	"encoding/json"
	"log"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

type Skill struct {
	Id   int    `json:"id"`
	Text string `json:"text"`
}

const SELECT_ACTIVE_SKILLS = `SELECT skill_id, text FROM skill WHERE active = 1`

func ServeSkills(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	skills := GetActiveSkills()
	if skills == nil {
		logrus.Info("No skill data found")
		return
	}

	json.NewEncoder(w).Encode(skills)
}

func GetActiveSkills() *[]Skill {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConDataHome) {
		return nil
	}

	rows, err := database.ConDataHome.Connection.Query(SELECT_ACTIVE_SKILLS)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var contactInfo []Skill
	for rows.Next() {
		var data Skill
		if err := rows.Scan(
			&data.Id,
			&data.Text,
		); err != nil {
			logrus.Error(err)
			return nil
		}
		contactInfo = append(contactInfo, data)
	}

	if err := rows.Err(); err != nil {
		log.Fatal(err)
	}

	return &contactInfo

}
