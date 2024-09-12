package users

import (
	"encoding/json"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"git.rickiekarp.net/rickie/home/pkg/utils"
	"github.com/sirupsen/logrus"
)

type Contact struct {
	Name     string `json:"name"`
	Email    string `json:"email"`
	Role     string `json:"role"`
	Location string `json:"location"`
}

type User struct {
	Id               int     `json:"id"`
	UserName         string  `json:"username"`
	Password         *string `json:"password"`
	Token            *string `json:"token"`
	AccountType      byte    `json:"accountType"`
	IsAccountEnabled bool    `json:"isAccountEnabled"`
}

const INSERT = `CALL createUser(?, ?, ?, true)`
const FIND_BY_NAME = `SELECT u.id, u.username, u.password, t.access_token AS token, u.type, u.enabled FROM users u JOIN token t on u.id = t.user_id WHERE u.username = ?`

func Register(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	var credentials Credentials
	err := json.NewDecoder(r.Body).Decode(&credentials)
	if err != nil {
		logrus.Error("Could not decode credentials: ", err)
		w.WriteHeader(500)
		return
	}

	user := registerUser(credentials)
	if user != nil {
		// Issue a token for the user
		token := issueToken(user.Id)
		if token == nil {
			w.WriteHeader(500)
			json.NewEncoder(w).Encode("Token could not be created!")
			return
		}
		w.WriteHeader(201)
		json.NewEncoder(w).Encode(token)
		return
	} else {
		w.WriteHeader(500)
		json.NewEncoder(w).Encode("User could not be created!")
		return
	}
}

func registerUser(credentials Credentials) *User {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConLogin) {
		return nil
	}

	rows, err := database.ConLogin.Connection.Query(INSERT,
		credentials.UserName,
		utils.GenerateStrongPasswordHash(credentials.Password),
		2, // user role (1 = ADMIN, 2 = USER, 3 = TOOLUSER)
	)

	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	if err := rows.Err(); err != nil {
		logrus.Error(err)
		return nil
	}

	user := getUserByName(credentials.UserName)
	return user
}

func getUserByName(userName string) *User {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConLogin) {
		return nil
	}

	rows, err := database.ConLogin.Connection.Query(FIND_BY_NAME, userName)
	if err != nil {
		logrus.Error(err)
		return nil
	}
	defer rows.Close()

	var user User
	for rows.Next() {
		var fetchedUser User
		if err := rows.Scan(
			&fetchedUser.Id,
			&fetchedUser.UserName,
			&fetchedUser.Password,
			&fetchedUser.Token,
			&fetchedUser.AccountType,
			&fetchedUser.IsAccountEnabled,
		); err != nil {
			logrus.Error(err)
			return nil
		}
		user = fetchedUser
	}

	if err := rows.Err(); err != nil {
		logrus.Error(err)
		return nil
	}

	return &user
}
