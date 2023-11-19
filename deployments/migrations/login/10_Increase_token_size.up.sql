-- General migration

ALTER TABLE users
MODIFY COLUMN token varchar(100) DEFAULT NULL;