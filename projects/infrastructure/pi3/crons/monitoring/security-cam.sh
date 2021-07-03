#!/bin/bash

imagedir="/mnt/camera/cameraCloud/Camera"

#capture
curTime=$(date +%H%M%S)
curYear=$(date +%Y)
curMonth=$(date +%m)
curDay=$(date +%d)

if [ ! -d "$imagedir/$curYear/$curMonth/$curDay" ] 
then
    mkdir -p $imagedir/$curYear/$curMonth/$curDay
fi

raspistill -vf -hf -o $imagedir/$curYear/$curMonth/$curDay/image_$curTime.jpg

# check for drive space
driveSpacePercentage=$(df -h | grep /mnt | grep -Eo '[0-9]&{0,9}%' | cut -d "%" -f1)
if [ "$driveSpacePercentage" -gt "70" ]
then
  oldestFiles=$(find $imagedir/ -type f -printf '%p\n' | sort | tail -n 100)
  rm -f $oldestFiles
fi