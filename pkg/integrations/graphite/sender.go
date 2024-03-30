package graphite

import (
	"fmt"

	"github.com/sirupsen/logrus"
)

var (
	Host = "rickiekarp.net"
	Port = 2003
)

// SendMetric sends a metric to graphite
func SendMetric(metric map[string]float64, prefix string) {
	graphiteClient := NewClient(Host, Port, prefix, "tcp")

	// sends the given metric map to the configured graphite
	if err := graphiteClient.SendData(metric); err != nil {
		logrus.Error(fmt.Sprintf("Error sending metrics: %v", err))
	}
}
