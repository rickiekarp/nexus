package api

import (
	"log"
	"time"

	"git.rickiekarp.net/rickie/home/projects/go/src/database"
	_ "github.com/go-sql-driver/mysql"
)

const GET_HIGHSCORES = "SELECT id, name, points, dateAdded FROM highscore order by points desc limit 50"
const ADD_HIGHSCORE = "INSERT INTO highscore (name, points) VALUES ($1, $2)"

type Highscore struct {
	Id     int       `json:"id"`
	Name   string    `json:"name"`
	Points int       `json:"points"`
	Date   time.Time `json:"dateAdded"`
}

func GetRanking() *[]Highscore {
	results, err := database.DB.Query(GET_HIGHSCORES)
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic in your app
	}

	var emps []Highscore

	for results.Next() {
		var emp Highscore
		// for each row, scan the result into our tag composite object
		err = results.Scan(&emp.Id, &emp.Name, &emp.Points, &emp.Date)
		if err != nil {
			panic(err.Error()) // proper error handling instead of panic in your app
		}
		emps = append(emps, emp)
		// and then print out the tag's Name attribute
		//log.Printf("%s %d %d %s", emp.Name, emp.Id, emp.Points, emp.Date)
	}

	return &emps
}

func AddHighscore(name string, points int) {
	statement, err := database.DB.Prepare("INSERT INTO highscore (name, points) VALUES(?,?)")
	if err != nil {
		panic(err.Error())
	}
	statement.Exec(name, points)
	log.Println("Added:", name, points)
}
