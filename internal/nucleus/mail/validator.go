package mail

import (
	"net/http"
	"strconv"

	"github.com/sirupsen/logrus"
)

func checkNotificationToken(w http.ResponseWriter, r *http.Request, tokenToValidate string) bool {
	val, ok := r.Header["X-Notification-Token"]
	if ok {
		// check if token is present in the database
		isTokenInSystem := CheckNotificationTokenExistence(val[0])
		if !isTokenInSystem {
			logrus.Info("X-Notification-Token key header is present with value ", val[0], ", but not found in the system!")
			w.WriteHeader(401)
			return false
		}
	} else {
		logrus.Info("X-Notification-Token missing!")
		w.WriteHeader(401)
		return false
	}
	return true
}

func checkUserIdHeader(w http.ResponseWriter, r *http.Request) (bool, int) {
	val, ok := r.Header["X-User-Id"]
	if ok {
		if userId, err := strconv.Atoi(val[0]); err == nil {
			return true, userId
		}
		w.WriteHeader(400)
	} else {
		logrus.Info("X-User-Id missing!")
		w.WriteHeader(400)
	}
	return false, 0
}
