package webpage

import (
	"encoding/json"
	"log"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

type ResumeEntry struct {
	Id          int                    `json:"id"`
	StartDate   string                 `json:"startDate"`
	EndDate     *string                `json:"endDate"`
	Name        string                 `json:"name"`
	JobTitle    string                 `json:"jobTitle"`
	Description ResumeEntryDescription `json:"description"`
}

type ResumeEntryDescription struct {
	Description string   `json:"description"`
	Tasks       []string `json:"tasks"`
}

const SELECT_RESUME = `select e.experience_id, e.startDate, e.endDate, c.name, j.title, e.description from experience e
join company c ON c.company_id = e.companyid
join job j ON j.job_id = e.jobid
where c.type = ? order by e.experience_id desc`

func ServeExperience(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	resume := GetResume("experience")
	if resume == nil {
		logrus.Info("No resume data found")
		return
	}

	json.NewEncoder(w).Encode(resume)
}

func ServeEducation(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	resume := GetResume("education")
	if resume == nil {
		logrus.Info("No resume data found")
		return
	}

	json.NewEncoder(w).Encode(resume)
}

func GetResume(resumeType string) *[]ResumeEntry {
	// check if the database is available
	if !database.CheckDatabaseConnection() {
		return nil
	}

	rows, err := database.ConDataHome.Query(SELECT_RESUME, resumeType)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var contactInfo []ResumeEntry

	for rows.Next() {
		var data ResumeEntry
		if err := rows.Scan(
			&data.Id,
			&data.StartDate,
			&data.EndDate,
			&data.Name,
			&data.JobTitle,
			&data.Description,
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

// scan for custom type
func (r *ResumeEntryDescription) Scan(src interface{}) error {
	return parseJSONToModel(src, r)
}

func parseJSONToModel(src interface{}, dest interface{}) error {
	var data []byte

	if b, ok := src.([]byte); ok {
		data = b
	} else if s, ok := src.(string); ok {
		data = []byte(s)
	} else if src == nil {
		return nil
	}

	return json.Unmarshal(data, dest)
}
