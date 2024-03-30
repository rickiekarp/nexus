package stats

import (
	"time"

	"git.rickiekarp.net/rickie/home/pkg/command"
	"git.rickiekarp.net/rickie/home/pkg/config"
	"git.rickiekarp.net/rickie/home/pkg/integrations/graphite"
	"github.com/sirupsen/logrus"
)

var uptimeFormatLayout = "2006-01-02 15:04:05"
var Uptime = time.Now()

func SetUptime() {
	stdOut, _, commandErr := command.ExecuteCmd("uptime", "-s")
	if commandErr > 0 {
		logrus.Error(commandErr)
		return
	}

	uptime, err := time.Parse(uptimeFormatLayout, stdOut)
	if err != nil {
		logrus.Error(err)
		return
	}

	Uptime = uptime
}

func SendUptimeMetric() {
	graphite.SendMetric(map[string]float64{"uptime": time.Since(Uptime).Minutes()}, "project6.agents."+config.HostName)
}
