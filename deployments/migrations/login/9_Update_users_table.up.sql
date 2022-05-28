-- General migration

ALTER TABLE users
CHANGE dateCreated dateCreated timestamp NOT NULL default CURRENT_TIMESTAMP;