#!/bin/bash
echo "Raid Backup Script v2023.4"

user="$USER"
workdir=`pwd`
echo "User name: $user"
echo "Working directory: $workdir"

driveA="/media/rickie/backup1/"
if [ -d "$driveA" ]; then
  echo "Starting backup to $driveA!"
  rsync -rlvpt --delete \
    --exclude 'applications/cloud/data/media' \
    --exclude 'applications/gogs' \
    --exclude 'applications/jenkins' \
    root@pi:/mnt/raid1/archive :/mnt/raid1/applications \
    $driveA 
else
  echo "---------------------------------------------------"
  echo "WARNING! Directory $driveA not found! Backup skipped!"
  echo "---------------------------------------------------"
fi

driveB="/media/rickie/backup2/"
if [ -d "$driveB" ]; then
  echo "Starting backup to $driveB!"
  rsync -rlvpt --delete \
    root@pi:/mnt/raid1/applications/cloud/data/media \
    $driveB 
else
  echo "---------------------------------------------------"
  echo "WARNING! Directory $driveB not found! Backup skipped!"
  echo "---------------------------------------------------"
fi
