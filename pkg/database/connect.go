package database

import (
	"database/sql"
	"fmt"

	_ "github.com/go-sql-driver/mysql"
)

func GetConnection(username, password, url, database string) (*sql.DB, error) {
	con, err := sql.Open("mysql", fmt.Sprintf(
		"%s:%s@tcp(%s)/%s",
		username,
		password,
		url,
		database,
	))
	if err != nil {
		return nil, err
	}

	if err = con.Ping(); err != nil {
		return nil, err
	}

	return con, err
}
