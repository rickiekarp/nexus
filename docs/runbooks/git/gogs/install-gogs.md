# README #

Installing gogs on raspberry pi

# Create docker volume.
$ docker volume create --name gogs-data

# Start gogs
docker run -d \
    --name gogs \
    -p 2222:22 -p 10080:3000 \
    -e PUID=1001 -e PGID=1001 \
    -v gogs-data:/data \
    gogs/gogs-rpi