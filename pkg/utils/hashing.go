package utils

import (
	"crypto/rand"
	"crypto/sha1"
	"encoding/hex"

	"golang.org/x/crypto/pbkdf2"
)

func GenerateStrongPasswordHash(password string) string {
	pbkdf2Hash := pbkdf2.Key([]byte(password), []byte(password), 2048, 25, sha1.New)
	return toHex(pbkdf2Hash)
}

func toHex(array []byte) string {
	return hex.EncodeToString(array)
}

func RandomAlphabetic(length int) string {
	b := make([]byte, length)
	if _, err := rand.Read(b); err != nil {
		return ""
	}
	return hex.EncodeToString(b)
}
