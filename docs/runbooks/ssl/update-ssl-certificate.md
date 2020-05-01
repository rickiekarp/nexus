# README #

Updating ssl certificate

### How To (Manually) ###

- ssh into server

- cd operation
- sudo ./certbot-auto -d rickiekarp.net -d *.rickiekarp.net --manual --preferred-challenges dns-01 --server https://acme-v02.api.letsencrypt.org/directory certonly
- follow the steps on screen

