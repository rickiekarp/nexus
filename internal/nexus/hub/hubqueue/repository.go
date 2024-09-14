package hubqueue

import (
	"database/sql"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

const FIND_BY_CHECKSUM = `SELECT * FROM files WHERE checksum = ?`
const INSERT = `INSERT INTO files (path, name, size, mtime, checksum) values (?, ?, ?, ?, ?)`

func FindFileInStorage(checksum string) *FileStorageEventMessage {
	if !database.CheckDatabaseConnection(database.ConStorage) {
		return nil
	}

	var storageEntry FileStorageEventMessage
	if err := database.ConStorage.Connection.QueryRow(FIND_BY_CHECKSUM, checksum).Scan(
		&storageEntry.Id,
		&storageEntry.Path,
		&storageEntry.Name,
		&storageEntry.Size,
		&storageEntry.Mtime,
		&storageEntry.Checksum,
		&storageEntry.Inserttime); err == nil {
	} else if err == sql.ErrNoRows {
		return nil
	} else {
		logrus.Error(err)
		return nil
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
