package webpage

import (
	"encoding/json"
	"log"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

type Contact struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Role     string `json:"role"`
	Location string `json:"location"`
}

func ServeContactInfo(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	contactInfo := GetContactInformation()
	if contactInfo == nil {
		logrus.Info("No reminder data found for userId ")
		return
	}

	json.NewEncoder(w).Encode(contactInfo)
}

const FIND_FIRST_CONTACT = `SELECT c.name, c.email, j.title, lc.city AS location 
FROM contact c 
join experience e 
join job j on e.jobid = j.job_id 
join location_junction lj on e.location_id = lj.id
join location_city lc on lj.city_id = lc.id
where c.contact_id = 1 ORDER BY e.experience_id desc limit 1`

func GetContactInformation() *Contact {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConDataHome) {
		return nil
	}

	rows, err := database.ConDataHome.Connection.Query(FIND_FIRST_CONTACT)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var contactInfo Contact
	for rows.Next() {
		var data Contact
		if err := rows.Scan(
			&data.Name,
			&data.Email,
			&data.Role,
			&data.Location,
		); err != nil {
			logrus.Error(err)
			return nil
		}
		contactInfo = data
	}

	if err := rows.Err(); err != nil {
		log.Fatal(err)
	}

	return &contactInfo
}
