package vault

import (
	"time"
)

type VaultEntry struct {
	Id         int     `json:"id"`
	Identifier string  `json:"identifier"`
	Token      string  `json:"token"`
	Type       *string `json:"type"`
	ValidUntil *int64  `json:"validUntil"`
	LastAccess *int64  `json:"lastAccess"`
	CreatedAt  int64   `json:"createdAt"`
}

func (ve *VaultEntry) isTokenSame(token string) bool {
	return ve.Token == token
}

func (ve *VaultEntry) isTokenValidUntil() bool {
	return ve.ValidUntil == nil || *ve.ValidUntil > time.Now().Unix()
}
