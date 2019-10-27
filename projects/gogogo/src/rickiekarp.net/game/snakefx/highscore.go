package snakefx

import (
	"encoding/json"
	"net/http"
	"time"

	"github.com/gorilla/mux"
)

type Highscore struct {
	Name   string    `json:"name,omitempty"`
	Points int       `json:"points,omitempty"`
	Date   time.Time `json:"date,omitempty"`
}

var emps []Highscore

func GetEmps(w http.ResponseWriter, r *http.Request) {
	emps = append(emps, Highscore{"UserA", 23, time.Now()})
	emps = append(emps, Highscore{"UserB", 12, time.Now()})
	emps = append(emps, Highscore{"UserC", 18, time.Now()})
	json.NewEncoder(w).Encode(emps)
}

func GetEmp(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	for _, emp := range emps {
		if emp.Name == params["name"] {
			json.NewEncoder(w).Encode(emp)
			return
		}
	}
	json.NewEncoder(w).Encode(&Highscore{})
}

func CreateEmp(w http.ResponseWriter, r *http.Request) {
	var emp Highscore
	_ = json.NewDecoder(r.Body).Decode(&emp)
	emps = append(emps, emp)
	json.NewEncoder(w).Encode(emp)
}

func DeleteEmp(w http.ResponseWriter, r *http.Request) {
	params := mux.Vars(r)
	for index, emp := range emps {
		if emp.Name == params["name"] {
			emps = append(emps[:index], emps[index+1:]...)
			break
		}
		json.NewEncoder(w).Encode(emps)
	}
}
