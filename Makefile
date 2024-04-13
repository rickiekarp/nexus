GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get
BUILD_PATH=build
BINARY_NAME=app
DEPLOY_DIR=/mnt/raid2/nodes/raspberrypi/deployment/

# Nexus

buildNexusARM64v7:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 GOARM=7 \
		go build -ldflags="-X git.rickiekarp.net/rickie/home/internal/nexus/config.Version=$(shell date +%Y%j%H%m) \
		-X git.rickiekarp.net/rickie/home/internal/nexus/config.Build=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/internal/nexus/config.ConfigBaseDir=data/config/ \
		-X git.rickiekarp.net/rickie/home/internal/nexus/config.ResourcesBaseDir=data/assets/web/" \
		-o $(BUILD_PATH)/output/nexus/$(BINARY_NAME) \
		cmd/nexus/main.go
		mkdir -p $(BUILD_PATH)/output/nexus/data/assets/web
		cp -r web/nexus/* $(BUILD_PATH)/output/nexus/data/assets/web
		cp -r deployments/module-deployment/values/nexus/prod/* $(BUILD_PATH)/output/nexus/data/
		cp deployments/docker/Dockerfile_goscratch build/output/nexus/Dockerfile

deployNexus:
		rsync -rlvpt --delete build/output/nexus pi:$(DEPLOY_DIR)

# Project6

## AMD64

buildProject6AMD64:
		CGO_ENABLED=0 GOOS=linux GOARCH=amd64 \
		go build -ldflags="-X git.rickiekarp.net/rickie/home/internal/project6/config.Version=$(shell date +%Y%j%H%m) \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.Build=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.ConfigBaseDir=data/config/ \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.DefaultLockFile=project6svc.lock" \
		-o $(BUILD_PATH)/output/project6/$(BINARY_NAME) \
		cmd/project6/main.go

deployProject6AMD64:
		rsync -rlvpt --delete build/output/project6/app pi@rickiekarp.net:/mnt/raid2/applications/nginx/files/software/dev/project6/amd64/project6

## ARM64

buildProject6ARM64:
		CGO_ENABLED=0 GOOS=linux GOARCH=arm64 \
		go build -ldflags="-X git.rickiekarp.net/rickie/home/internal/project6/config.Version=$(shell date +%Y%j%H%m) \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.Build=$(shell git rev-parse HEAD) \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.ConfigBaseDir=data/config/ \
		-X git.rickiekarp.net/rickie/home/internal/project6/config.DefaultLockFile=project6svc.lock" \
		-o $(BUILD_PATH)/output/project6/$(BINARY_NAME) \
		cmd/project6/main.go

deployProject6ARM64:
		rsync -rlvpt --delete build/output/project6/app pi@rickiekarp.net:/mnt/raid2/applications/nginx/files/software/dev/project6/arm64/project6

# Other

test:
		$(GOTEST) -v ./...

clean:
		$(GOCLEAN)
		rm -rf $(BUILD_PATH)
