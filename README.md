# README #

Monorepo for all my home server projects


## Set up hosts
```
echo '127.0.0.1 database' | sudo tee -a /etc/hosts
```

## Set up docker-compose

Start required services locally (needs module-deployment to be present)
```
docker-compose -f deployments/docker-compose.yml build
docker-compose -f deployments/docker-compose.yml up
```

## Migrate databse

```
cd deployments/migrations
make getimage
make migrate HOST=database DATABASE=login DBUSER=root DBPASS=root
make migrate HOST=database DATABASE=gamedata DBUSER=root DBPASS=root
make migrate HOST=database DATABASE=data_home DBUSER=root DBPASS=root
```

## Set up IDE

### VSCode

If you are using go installed from a snap, you might need to add a `.vscode/settings.json` file to your project to set the correct go tools path.
```
{
    "go.alternateTools": {
        "go": "/snap/go/current/bin/go"
    }
}
```