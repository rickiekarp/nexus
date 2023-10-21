package config

var (
	Version          = "development"                                              // Version set during go build using ldflags
	ConfigBaseDir    = "deployments/module-deployment/values/nucleus/dev/config/" // ConfigBaseDir set during go build using ldflags
	ResourcesBaseDir = "web/nucleus/"
)
