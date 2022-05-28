#!/bin/bash

imagedir="/mnt/camera/cameraCloud/Camera"

while [ true ]
do
  #capture
  curTime=$(date +%H%M%S)
  curYear=$(date +%Y)
  curMonth=$(date +%m)
  curDay=$(date +%d)

  raspistill -vf -hf -o $imagedir/$curYear/$curMonth/$curDay/image_$curTime.jpg
  echo "Created $imagedir/$curYear/$curMonth/$curDay/image_$curTime.jpg"
done