package database

import (
	"database/sql"
	"fmt"
	"log"
)

// Create an exported global variable to hold the database connection pool.
var DB *sql.DB

func GetConnection(username, password, url, database string) {
	var err error
	DB, err = sql.Open("mysql", fmt.Sprintf(
		"%s:%s@tcp(%s:3306)/%s",
		username,
		password,
		url,
		database,
	))
	if err != nil {
		log.Panic(err)
	}

	if err = DB.Ping(); err != nil {
		log.Panic(err)
	}
}
