package blockchain

import (
	"os"

	"git.rickiekarp.net/rickie/home/internal/nexus/config"
	"github.com/sirupsen/logrus"
	"gopkg.in/yaml.v2"
)

type blockChainConfig struct {
	Defaults struct {
		StakeReward int64 `yaml:"stakeReward"`
	} `yaml:"defaults"`
	Storage struct {
		Type string `yaml:"type"`
		Path string `yaml:"path"`
	} `yaml:"storage"`
}

var (
	Version       = "1"                                                           // Version set during go build using ldflags
	Build         = "development"                                                 // Build set during go build using ldflags
	ConfigBaseDir = "deployments/module-deployment/values/nexuschain/dev/config/" // ConfigBaseDir set during go build using ldflags
	Hostname      string
)

func ReadNodeByAddress(address string) (*Node, error) {
	var node *Node
	yamlFile, err := os.ReadFile(config.NexusConf.NexusChain.Storage.Path + "/nodes/" + address)
	if err != nil {
		logrus.Printf("yamlFile.Get err   #%v ", err)
		return nil, err
	}
	err = yaml.Unmarshal(yamlFile, &node)
	if err != nil {
		logrus.Printf("Unmarshal: %v", err)
		return nil, err
	}

	return node, nil
}
