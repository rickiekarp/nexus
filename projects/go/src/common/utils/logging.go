package utils

import (
	"log"
	"os"
)

func CheckFile(logFile string) (*os.File, error) {
	f, err := os.OpenFile(logFile, os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
	if err != nil {
		log.Println("error opening file:", err)
		f, err = getFallbackLog()
	}
	return f, err
}

func getFallbackLog() (*os.File, error) {
	home, err := os.UserHomeDir()
	if err != nil {
		log.Fatalf("error when getting home directory")
	}

	f, err := os.OpenFile(home+"/fallback.log", os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
	log.Println("Writing to fallback log " + home + "/fallback.log")
	return f, err
}
