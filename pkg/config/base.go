package config

import (
	"fmt"
	"os"
)

const (
	// Time allowed to write a message to the peer.
	FileStorageBaseUrl = "http://files.rickiekarp.net"
)

var (
	HostName = ""
)

func init() {
	hostname, err := os.Hostname()
	if err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
	HostName = hostname
}
