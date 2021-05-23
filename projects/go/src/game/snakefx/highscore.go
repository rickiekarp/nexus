package snakefx

import (
	"encoding/json"
	"log"
	"net/http"
)

func GetEmps(w http.ResponseWriter, r *http.Request) {
	emps := GetRanking(db)
	json.NewEncoder(w).Encode(emps)
}

func GetEmp(w http.ResponseWriter, r *http.Request) {
	// params := mux.Vars(r)
	// for _, emp := range emps {
	// 	if emp.Name == params["name"] {
	// 		json.NewEncoder(w).Encode(emp)
	// 		return
	// 	}
	// }
	// json.NewEncoder(w).Encode(&Highscore{})
}

func CreateEmp(w http.ResponseWriter, r *http.Request) {
	var emp Highscore
	_ = json.NewDecoder(r.Body).Decode(&emp)
	json.NewEncoder(w).Encode(emp)
	log.Println(emp)
	AddHighscore(db, emp.Name, emp.Points)
}

func DeleteEmp(w http.ResponseWriter, r *http.Request) {
	// params := mux.Vars(r)
	// for index, emp := range emps {
	// 	if emp.Name == params["name"] {
	// 		emps = append(emps[:index], emps[index+1:]...)
	// 		break
	// 	}
	// 	json.NewEncoder(w).Encode(emps)
	// }
}
