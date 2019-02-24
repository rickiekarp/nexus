#!/usr/bin/env bash
docker run -d --name website -p 8080:8080 --net="host" rickiekarp-website:latest