GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app
DEPLOY_DIR=/mnt/raid2/nodes/raspberrypi/deployment/nexus/

# Nexus

buildNexusARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X git.rickiekarp.net/rickie/home/internal/nexus/config.Version=$(shell date +%Y%j%H%m) \
		-X git.rickiekarp.net/rickie/home/internal/nexus/config.Build=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/internal/nexus/config.ConfigBaseDir=data/config/ \
		-X git.rickiekarp.net/rickie/home/internal/nexus/config.ResourcesBaseDir=data/assets/web/" \
		-o $(BUILD_PATH)/$(BINARY_NAME) main.go
		mkdir -p $(BUILD_PATH)/data/assets/web
		cp -r web/nexus/* $(BUILD_PATH)/data/assets/web
		cp -r deployments/module-deployment/values/nexus/prod/* $(BUILD_PATH)/data/
		cp deployments/docker/Dockerfile_goscratch build/Dockerfile

deployNexus:
		rsync -rlvpt --delete build/* pi:$(DEPLOY_DIR)

# Other

test:
		$(GOTEST) -v ./...

clean:
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)
