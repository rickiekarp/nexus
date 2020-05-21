# Go parameters
GOCMD=go
GOBUILD=$(GOCMD) build
GOCLEAN=$(GOCMD) clean
GOTEST=$(GOCMD) test
GOGET=$(GOCMD) get

MIGRATE_VERSION=v4.11.0

MIGRATE_MODE?=up
MIGRATE_STEPS?=1

all: getimage
getimage: 
		docker pull migrate/migrate:$(MIGRATE_VERSION)

enterContainer: check-db
		echo "Enter migration container"
		docker run -it -v $(CURDIR)/$(DATABASE):/migrations --network host --entrypoint /bin/sh migrate/migrate

migrate: check-env check-db check-credentials
		echo "Migrating $(DATABASE) db $(MIGRATE_STEPS) version $(MIGRATE_MODE)"
		docker run -v $(CURDIR)/$(DATABASE):/migrations --network host migrate/migrate -verbose -path=/migrations/ -database "mysql://$(DBUSER):$(DBPASS)@tcp($(HOST):3306)/$(DATABASE)" $(MIGRATE_MODE) $(MIGRATE_STEPS)


# Check for missing target arguments
check-env:
ifndef HOST
	$(error HOST is undefined)
endif

check-db:
ifndef DATABASE
	$(error DATABASE is undefined)
endif

check-credentials:
ifndef DBUSER
	$(error DBUSER is undefined)
endif
ifndef DBPASS
	$(error DBPASS is undefined)
endif