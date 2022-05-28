GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app

buildSysmonAmd64:
		CGO_ENABLED=0 GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) -X main.ConfigBaseDir=config/" \
		-o $(BUILD_PATH)/output/sysmon/$(BINARY_NAME) \
		cmd/sysmon/main.go
		cp -r deployments/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/sysmon/
		cp deployments/docker/Dockerfile_goscratch build/output/sysmon/Dockerfile

buildSysmonARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) -X main.ConfigBaseDir=config/" \
		-o $(BUILD_PATH)/output/sysmon/$(BINARY_NAME) \
		cmd/sysmon/main.go
		cp -r deployments/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/sysmon/
		cp deployments/docker/Dockerfile_goscratch build/output/sysmon/Dockerfile

deploySysmon:
		rsync -rlvpt --delete build/output/sysmon pi:~/apps/

buildMailServiceARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) -X git.rickiekarp.net/rickie/home/services/mailsvc/config.ConfigBaseDir=config/" \
		-o $(BUILD_PATH)/output/mailsvc/$(BINARY_NAME) \
		cmd/mailsvc/main.go
		cp -r deployments/module-deployment/values/services/mailsvc/prod/* $(BUILD_PATH)/output/mailsvc/
		cp deployments/docker/Dockerfile_goscratch build/output/mailsvc/Dockerfile

deployMailService:
		rsync -rlvpt --delete build/output/mailsvc pi:~/apps/

test:
		$(GOTEST) -v ./...

clean:
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)
