# README #

Monorepo for all my personal projects


## Set up hosts
```
echo '127.0.0.1 database' | sudo tee -a /etc/hosts
```

## Set up database

```
cd projects/module-deployment
make mariadb-build
make mariadb-run
```

## Migrate databse

```
cd projects/migrations
make getimage
make migrate HOST=database DATABASE=login DBUSER=root DBPASS=root
make migrate HOST=database DATABASE=gamedata DBUSER=root DBPASS=root
make migrate HOST=database DATABASE=data_home DBUSER=root DBPASS=root
```