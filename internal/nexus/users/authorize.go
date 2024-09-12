package users

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"net/http"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"git.rickiekarp.net/rickie/home/pkg/utils"
	"github.com/sirupsen/logrus"
)

type Credentials struct {
	UserName string `json:"username"`
	Password string `json:"password"`
}

type Token struct {
	Token string `json:"token"`
}

const UPDATE = `UPDATE token SET access_token = ?, lastUpdated = now() WHERE user_id = ?`

func Authorize(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	var credentials Credentials
	err := json.NewDecoder(r.Body).Decode(&credentials)
	if err != nil {
		w.WriteHeader(500)
		logrus.Error(err)
		return
	}

	// Authenticate the user using the credentials provided
	user := findUser(credentials)
	if user == nil {
		w.WriteHeader(500)
		return
	}

	// Issue a token for the user
	token := issueToken(user.Id)
	if token != nil {
		// Return the token on the response
		w.WriteHeader(200)
		json.NewEncoder(w).Encode(token)
		return
	}

	logrus.Error("Could not issue token for user: ", user.Id)
}

func findUser(credentials Credentials) *User {

	// Authenticate against a database, LDAP, file or whatever
	// Throw an Exception if the credentials are invalid
	retrievedUser := getUserByName(credentials.UserName)
	if retrievedUser != nil {
		if retrievedUser.IsAccountEnabled {
			// validate credentials
			if utils.GenerateStrongPasswordHash(credentials.Password) == *retrievedUser.Password {
				return retrievedUser
			} else {
				logrus.Error("Wrong password for user: ", retrievedUser.Id)
				return nil
			}
		} else {
			logrus.Error("Account is disabled: ", retrievedUser.Id)
			return nil
		}
	} else {
		logrus.Error("No account found!")
		return nil
	}
}

func issueToken(userId int) *Token {
	// Issue a token (can be a random String persisted to a database or a JWT token)
	// The issued token must be associated to a user
	// Return the issued token
	randomBytes := utils.RandomAlphabetic(25)
	token := base64.StdEncoding.EncodeToString([]byte(fmt.Sprint(userId) + ":" + randomBytes))

	wasUpdated := updateUserToken(userId, token)
	if wasUpdated {
		return &Token{Token: token}
	} else {
		logrus.Error("Token could not be updated!")
		return nil
	}
}

func updateUserToken(userId int, token string) bool {
	// check if the database is available
	if !database.CheckDatabaseConnection(database.ConLogin) {
		return false
	}

	rows, err := database.ConLogin.Connection.Query(UPDATE, token, userId)
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
