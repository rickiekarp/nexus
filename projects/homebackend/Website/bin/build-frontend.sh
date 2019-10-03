#!/usr/bin/env bash

echo "Removing old website files..."
rm -rf ../module-deployment/html

echo "Building website files..."
cd WebsiteFrontend
ng build --prod --output-path=../../module-deployment/html