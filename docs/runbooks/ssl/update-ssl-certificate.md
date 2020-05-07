# README #

Updating ssl certificate

### How To (Manually) ###

- ssh into server

- cd operation
- sudo ./certbot-auto -d rickiekarp.net -d *.rickiekarp.net --manual --preferred-challenges dns-01 --server https://acme-v02.api.letsencrypt.org/directory certonly
- follow the steps on screen

### How To (Automatic) ###

To obtain a new or tweaked
   version of this certificate in the future, simply run certbot-auto again.

To non-interactively renew *all* of your certificates, run
   "certbot-auto renew"
