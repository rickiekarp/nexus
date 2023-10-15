GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app
DEPLOY_DIR=/mnt/raid2/applications/apps/

# Sysmon

buildSysmonARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) \
		-X main.ConfigBaseDir=data/config/" \
		-o $(BUILD_PATH)/output/sysmon/$(BINARY_NAME) \
		cmd/sysmon/main.go
		mkdir -p $(BUILD_PATH)/output/sysmon/data
		cp -r deployments/module-deployment/values/sysmon/prod/* $(BUILD_PATH)/output/sysmon/data/
		cp deployments/docker/Dockerfile_goscratch build/output/sysmon/Dockerfile

deploySysmon:
		rsync -rlvpt --delete build/output/sysmon pi:$(DEPLOY_DIR)

# MailSvc

buildMailServiceARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/services/mailsvc/config.ConfigBaseDir=data/config/" \
		-o $(BUILD_PATH)/output/mailsvc/$(BINARY_NAME) \
		cmd/mailsvc/main.go
		mkdir -p $(BUILD_PATH)/output/mailsvc/data
		cp -r deployments/module-deployment/values/mailsvc/prod/* $(BUILD_PATH)/output/mailsvc/data/
		cp deployments/docker/Dockerfile_goscratch build/output/mailsvc/Dockerfile

deployMailService:
		rsync -rlvpt --delete build/output/mailsvc pi:$(DEPLOY_DIR)

# Launchpad

buildLaunchpadARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X main.Version=$(shell git rev-parse HEAD) \
		-X main.ConfigBaseDir=data/config/ \
		-X main.ResourcesBaseDir=data/assets/web/" \
		-o $(BUILD_PATH)/output/launchpad/$(BINARY_NAME) \
		cmd/launchpad/main.go
		mkdir -p $(BUILD_PATH)/output/launchpad/data/assets/web
		cp -r web/launchpad/* $(BUILD_PATH)/output/launchpad/data/assets/web
		cp -r deployments/module-deployment/values/launchpad/prod/* $(BUILD_PATH)/output/launchpad/data/
		cp deployments/docker/Dockerfile_goscratch build/output/launchpad/Dockerfile

deployLaunchpad:
		rsync -rlvpt --delete build/output/launchpad pi:$(DEPLOY_DIR)

# Other

test:
		$(GOTEST) -v ./...

clean:
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)
