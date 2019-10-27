package snakefx

import (
	"database/sql"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

type Tag struct {
	ID   int    `json:"id"`
	Name string `json:"username"`
}

func GetRanking() {
	// Open up our database connection.
	db, err := sql.Open("mysql", "user:pass@tcp(database:3306)/gamedata")

	// if there is an error opening the connection, handle it
	if err != nil {
		log.Print(err.Error())
	}
	defer db.Close()

	// Execute the query
	results, err := db.Query("SELECT id, username FROM users")
	if err != nil {
		panic(err.Error()) // proper error handling instead of panic in your app
	}

	for results.Next() {
		var tag Tag
		// for each row, scan the result into our tag composite object
		err = results.Scan(&tag.ID, &tag.Name)
		if err != nil {
			panic(err.Error()) // proper error handling instead of panic in your app
		}
		// and then print out the tag's Name attribute
		log.Printf("%s %d", tag.Name, tag.ID)
	}

}
