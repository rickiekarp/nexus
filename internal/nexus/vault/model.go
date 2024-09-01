package vault

import (
	"time"
)

type VaultEntry struct {
	Id         int    `json:"id"`
	Identifier string `json:"identifier"`
	Token      string `json:"token"`
	ValidUntil *int64 `json:"validUntil"`
	LastAccess *int64 `json:"lastAccess"`
	CreatedAt  int64  `json:"createdAt"`
}

func (ve *VaultEntry) IsValidToken() bool {
	return ve.ValidUntil == nil || *ve.ValidUntil > time.Now().Unix()
}
