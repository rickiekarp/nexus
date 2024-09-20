package fileguardian

import (
	"crypto/md5"
	"fmt"
	"strconv"
	"time"
)

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
