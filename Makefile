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
		-X git.rickiekarp.net/rickie/home/internal/mailsvc/config.ConfigBaseDir=data/config/" \
		-o $(BUILD_PATH)/output/mailsvc/$(BINARY_NAME) \
		cmd/mailsvc/main.go
		mkdir -p $(BUILD_PATH)/output/mailsvc/data
		cp -r deployments/module-deployment/values/mailsvc/prod/* $(BUILD_PATH)/output/mailsvc/data/
		cp deployments/docker/Dockerfile_goscratch build/output/mailsvc/Dockerfile

deployMailService:
		rsync -rlvpt --delete build/output/mailsvc pi:$(DEPLOY_DIR)

# Nucleus

buildNucleusARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X git.rickiekarp.net/rickie/home/internal/nucleus/config.Version=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/internal/nucleus/config.ConfigBaseDir=data/config/ \
		-X git.rickiekarp.net/rickie/home/internal/nucleus/config.ResourcesBaseDir=data/assets/web/" \
		-o $(BUILD_PATH)/output/nucleus/$(BINARY_NAME) \
		cmd/nucleus/main.go
		mkdir -p $(BUILD_PATH)/output/nucleus/data/assets/web
		cp -r web/nucleus/* $(BUILD_PATH)/output/nucleus/data/assets/web
		cp -r deployments/module-deployment/values/nucleus/prod/* $(BUILD_PATH)/output/nucleus/data/
		cp deployments/docker/Dockerfile_goscratch build/output/nucleus/Dockerfile

deployNucleus:
		rsync -rlvpt --delete build/output/nucleus pi:$(DEPLOY_DIR)

# Project6

buildProject6AMD64:
		CGO_ENABLED=0 GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X git.rickiekarp.net/rickie/home/internal/project6/config.Version=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.ConfigBaseDir=data/config/" \
		-o $(BUILD_PATH)/output/project6/$(BINARY_NAME) \
		cmd/project6/main.go

deployProject6:
		rsync -rlvpt --delete build/output/project6 rickie:192.168.178.151:/home/rickie/Programs

# Other

test:
		$(GOTEST) -v ./...

clean:
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)
