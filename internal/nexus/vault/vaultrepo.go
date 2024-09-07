package vault

import (
	"database/sql"

	"git.rickiekarp.net/rickie/home/pkg/database"
	"github.com/sirupsen/logrus"
)

const FIND_BY_IDENTIFIER_AND_TYPE = `SELECT id, identifier, token, type, validUntil, lastAccess, createdAt FROM vault v WHERE identifier = ? and type = ?`
const UPDATE_KEY_ACCESSED = `UPDATE vault SET lastAccess = unix_timestamp() WHERE id = ?`

func UpdateVaultKeyAccessed(fetchedVaultEntry VaultEntry) bool {
	if !database.CheckDatabaseConnection(database.ConDataNexus) {
		return false
	}

	rows, err := database.ConDataNexus.Connection.Query(UPDATE_KEY_ACCESSED, fetchedVaultEntry.Id)
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

func FetchVaultEntry(identifier string, entryType string) *VaultEntry {
	if !database.CheckDatabaseConnection(database.ConDataNexus) {
		return nil
	}

	var fetchedUser VaultEntry
	if err := database.ConDataNexus.Connection.QueryRow(FIND_BY_IDENTIFIER_AND_TYPE, identifier, entryType).Scan(
		&fetchedUser.Id,
		&fetchedUser.Identifier,
		&fetchedUser.Token,
		&fetchedUser.Type,
		&fetchedUser.ValidUntil,
		&fetchedUser.LastAccess,
		&fetchedUser.CreatedAt,
	); err == nil {
	} else if err == sql.ErrNoRows {
		return nil
	} else {
		logrus.Error(err)
		return nil
	}

	// make sure the user was fetched correctly
	if fetchedUser.Id == 0 {
		return nil
	}

	if !UpdateVaultKeyAccessed(fetchedUser) {
		logrus.Warn("Could not update lastAccess for token, rejecting access")
		return nil
	}

	return &fetchedUser
}
