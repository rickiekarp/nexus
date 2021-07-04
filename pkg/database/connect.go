package database

import (
	"database/sql"
	"fmt"
)

func GetConnection(username, password, url, database string) (*sql.DB, error) {
	DB, err := sql.Open("mysql", fmt.Sprintf(
		"%s:%s@tcp(%s:3306)/%s",
		username,
		password,
		url,
		database,
	))
	if err != nil {
		return nil, err
	}

	if err = DB.Ping(); err != nil {
		return nil, err
	}

	return DB, err
}
