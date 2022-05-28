package graphite

import (
	"fmt"

	"git.rickiekarp.net/rickie/home/services/sysmon/config"
	"github.com/sirupsen/logrus"
)

// SendMetric sends a metric to graphite
func SendMetric(metric map[string]float64, prefix string) {
	graphiteClient := NewClient(config.SysmonConf.Graphite.Host, config.SysmonConf.Graphite.Port, prefix, "tcp")

	// sends the given metric map to the configured graphite
	if err := graphiteClient.SendData(metric); err != nil {
		logrus.Error(fmt.Sprintf("Error sending metrics: %v", err))
	}
}
