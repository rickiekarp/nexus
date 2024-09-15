package hubqueue

import (
	"database/sql"
	"strconv"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

const FIND_BY_CHECKSUM = `SELECT f.id, f.path, f.name, f.size, f.mtime, f.checksum, f.inserttime, fad.property, fad.value FROM files f JOIN files_additional_data fad ON f.id = fad.files_id WHERE f.checksum = ?`
const INSERT = `CALL insertFileToStorage(?, ?, ?, ?, ?)`
const UPDATE_ITERATION = `UPDATE files_additional_data set value = ? WHERE files_id = ? AND property = "iteration"`

func FindFileInStorage(checksum string) *FileStorageEventMessage {
	if !database.CheckDatabaseConnection(database.ConStorage) {
		return nil
	}

	rows, err := database.ConStorage.Connection.Query(FIND_BY_CHECKSUM, checksum)
	if err == sql.ErrNoRows {
		return nil
	} else if err != nil {
		logrus.Error(err)
		return nil
	}

	storageEntry := FileStorageEventMessage{AdditionalData: &[]FileStorageAdditionalDataEventMessage{}}

	for i := 0; rows.Next(); i++ {
		additionalData := FileStorageAdditionalDataEventMessage{}
		rows.Scan(
			&storageEntry.Id,
			&storageEntry.Path,
			&storageEntry.Name,
			&storageEntry.Size,
			&storageEntry.Mtime,
			&storageEntry.Checksum,
			&storageEntry.Inserttime,
			&additionalData.Property,
			&additionalData.Value,
		)
		additionalData.FilesId = storageEntry.Id
		*storageEntry.AdditionalData = append(*storageEntry.AdditionalData, additionalData)
	}

	// make sure the entry was fetched correctly
	if storageEntry.Id == nil {
		return nil
	}

	return &storageEntry
}

func InsertFile(fileStorageEventMessage FileStorageEventMessage) bool {
	if !database.CheckDatabaseConnection(database.ConStorage) {
		return false
	}

	rows, err := database.ConStorage.Connection.Query(INSERT,
		fileStorageEventMessage.Path,
		fileStorageEventMessage.Name,
		fileStorageEventMessage.Size,
		fileStorageEventMessage.Mtime,
		fileStorageEventMessage.Checksum,
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

func UpdateFileIteration(fileStorageEventMessage FileStorageEventMessage) bool {
	if !database.CheckDatabaseConnection(database.ConStorage) {
		return false
	}

	if fileStorageEventMessage.AdditionalData != nil {
		data := *fileStorageEventMessage.AdditionalData
		iterationsProperty := findIterationsProperty(data)
		iterations, err := strconv.Atoi(iterationsProperty.Value)
		if err != nil {
			logrus.Error(err)
			return false
		}

		rows, err := database.ConStorage.Connection.Query(UPDATE_ITERATION,
			strconv.Itoa(iterations+1),
			fileStorageEventMessage.Id,
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

	return false
}

func findIterationsProperty(data []FileStorageAdditionalDataEventMessage) *FileStorageAdditionalDataEventMessage {
	for i := 0; i < len(data); i++ {
		if data[i].Property == "iteration" {
			return &data[i]
		}
	}
	return nil
}
