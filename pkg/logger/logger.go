package logger

import (
	"os"

	"github.com/sirupsen/logrus"
)

func SetupDefaultLogger(logFile *string) {
	// Log as JSON instead of the default ASCII formatter.
	//logrus.SetFormatter(&logrus.JSONFormatter{})
	// Only log the debug severity or above.
	logrus.SetLevel(logrus.DebugLevel)

	logrus.StandardLogger().Out = os.Stdout

	if logFile != nil {
		file, err := os.OpenFile(*logFile, os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
		if err == nil {
			logrus.StandardLogger().Out = file
		} else {
			logrus.Info("Failed to log to file, using default stderr")
		}
	}
}
