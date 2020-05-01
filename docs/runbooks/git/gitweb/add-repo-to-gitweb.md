# README #

Setting up a new git repository in gitweb

### How To ###

- create new git repo

- ssh into git server
- sudo touch /var/www/git/REPODIR.git/gitweb-export-ok

- sudo chmod 2775 REPODIR
- sudo chown -R git:www-data REPODIR

- sudo nano REPODIR.git/description
	-> Add description
