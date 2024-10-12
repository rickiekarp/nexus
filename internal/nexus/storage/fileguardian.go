package storage

import (
	"crypto/md5"
	"database/sql"
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"
	"strings"
	"time"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"git.rickiekarp.net/rickie/nexusform"
	"github.com/sirupsen/logrus"
)

func FetchFileGuard(w http.ResponseWriter, r *http.Request) {
	logrus.Info("CALL:API:FetchFileGuard")

	var requestMessage *nexusform.FileGuardianEntry
	_ = json.NewDecoder(r.Body).Decode(&requestMessage)

	logrus.Info(requestMessage)

	if requestMessage.Source == "" && requestMessage.Target == "" {
		logrus.Warn("Can not process message: ", r.Body)
		w.WriteHeader(400)
		return
	}

	var responseMessage *nexusform.FileGuardianEntry = nil

	// we take the Source from the client and look if it exists as a target already
	responseMessage = FindFileGuardianEntry(requestMessage.Source, requestMessage.Context, false)
	if responseMessage == nil {
		if strings.HasSuffix(requestMessage.Source, ".fgd") {
			w.WriteHeader(http.StatusNotFound)
			return
		}

		// if the file was not found in the Targets, look if it exists in the Source
		retrySourceMessage := FindFileGuardianEntry(requestMessage.Source, requestMessage.Context, true)

		if retrySourceMessage != nil {
			json.NewEncoder(w).Encode(retrySourceMessage)
			return
		} else {

			if len(requestMessage.Type) == 0 {
				logrus.Warn("No type parameter provided")
				w.WriteHeader(400)
				return
			}

			// do not insert a new fileguardian entry if check parameter is present
			param := r.URL.Query().Get("check")
			if len(param) > 0 {
				w.WriteHeader(http.StatusOK)
				return
			}

			// generate Target hash
			requestMessage.Target = generateTarget(requestMessage.Source, requestMessage.Type, requestMessage.Context)
			// try to insert
			wasInserted := InsertFileGuard(*requestMessage)
			if wasInserted {
				responseMessage = FindFileGuardianEntry(requestMessage.Source, requestMessage.Context, true)
			} else {
				w.WriteHeader(http.StatusInternalServerError)
				return
			}
			json.NewEncoder(w).Encode(responseMessage)
		}
	} else {
		json.NewEncoder(w).Encode(responseMessage)
		return
	}
}

// Fileguardian

const FIND_FG_BY_SOURCE = `select id, type, source, target, context, inserttime from fileguardian where source = ? and context = ?`
const FIND_FG_BY_TARGET = `select id, type, source, target, context, inserttime from fileguardian where target = ? and context = ?`
const INSERT = `insert into fileguardian (type, source, target, context) values (?, ?, ?, ?);`

func FindFileGuardianEntry(name string, context string, isSource bool) *nexusform.FileGuardianEntry {
	if !database.CheckDatabaseConnection(database.ConStorage) {
		return nil
	}

	var query string
	if isSource {
		query = FIND_FG_BY_SOURCE
	} else {
		query = FIND_FG_BY_TARGET
	}

	rows, err := database.ConStorage.Connection.Query(query, name, context)
	if err == sql.ErrNoRows {
		return nil
	} else if err != nil {
		logrus.Error(err)
		return nil
	}

	storageEntry := nexusform.FileGuardianEntry{}

	for i := 0; rows.Next(); i++ {
		rows.Scan(
			&storageEntry.Id,
			&storageEntry.Type,
			&storageEntry.Source,
			&storageEntry.Target,
			&storageEntry.Context,
			&storageEntry.Inserttime,
		)
	}

	// make sure the entry was fetched correctly
	if storageEntry.Id == nil {
		return nil
	}

	return &storageEntry
}

func InsertFileGuard(fileGuard nexusform.FileGuardianEntry) bool {
	if !database.CheckDatabaseConnection(database.ConStorage) {
		return false
	}

	rows, err := database.ConStorage.Connection.Query(INSERT,
		fileGuard.Type,
		fileGuard.Source,
		fileGuard.Target,
		fileGuard.Context,
	)

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

func getMd5Sum(source string, idx int) [16]byte {
	return md5.Sum([]byte(source + "-" + strconv.Itoa(idx)))
}

func generateTarget(source string, entryType string, context string) string {
	switch entryType {
	case "dir":
		return fmt.Sprintf("%x", getMd5Sum(context+"-"+entryType+"-"+source, time.Now().UTC().Nanosecond()))
	case "file":
		return fmt.Sprintf("%x", getMd5Sum(context+"-"+entryType+"-"+source, time.Now().UTC().Nanosecond())) + ".fgd"
	}
	return ""
}
