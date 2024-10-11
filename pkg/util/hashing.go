package util

import (
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

func CalcSha1(text string) string {
	h := sha1.New()
	h.Write([]byte(text))
	sha1_hash := hex.EncodeToString(h.Sum(nil))
	return sha1_hash
}
