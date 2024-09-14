package users

import (
	"database/sql"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

const FIND_BY_TOKEN = `SELECT u.id, u.username, u.role_id, u.enabled FROM users u JOIN token t on u.id = t.user_id WHERE t.access_token = ? and u.role_id in (1,2)`
const DO_LOGIN = `UPDATE login SET lastLoginDate = now(), lastLoginIP = ? WHERE users_id = ?`

func Login(w http.ResponseWriter, req *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	user := getUserFromToken(req.Header.Get("Authorization"))
	if user == nil {
		w.WriteHeader(500)
		return
	}

	isSuccess := doLogin(req, *user)
	if isSuccess {
		w.WriteHeader(http.StatusNoContent)
	} else {
		w.WriteHeader(http.StatusUnauthorized)
	}
}

func doLogin(req *http.Request, fetchedUser User) bool {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConLogin) {
		return false
	}

	lastLoginIp := req.RemoteAddr

	rows, err := database.ConLogin.Connection.Query(DO_LOGIN, lastLoginIp, fetchedUser.Id)
	if err != nil {
		logrus.Error(err)
		return false
	}
	defer rows.Close()

	if err := rows.Err(); err != nil {
		logrus.Error(err)
		return false
	}

	return true
}

func getUserFromToken(token string) *User {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConLogin) {
		return nil
	}

	var fetchedUser User
	if err := database.ConLogin.Connection.QueryRow(FIND_BY_TOKEN, token).Scan(
		&fetchedUser.Id,
		&fetchedUser.UserName,
		&fetchedUser.AccountType,
		&fetchedUser.IsAccountEnabled); err == nil {
	} else if err == sql.ErrNoRows {
		logrus.Error(err)
		return nil
	} else {
		logrus.Error(err)
		return nil
	}

	// make sure the user was fetched correctly
	if fetchedUser.Id == 0 {
		return nil
	}

	return &fetchedUser
}
