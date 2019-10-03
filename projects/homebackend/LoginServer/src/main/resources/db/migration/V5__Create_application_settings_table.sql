CREATE TABLE applicationsettings(
   id 		        int unsigned    auto_increment   PRIMARY KEY,
   identifier       varchar(50)     NOT NULL,
   content          TEXT            NOT NULL,
   lastUpdated      DATE
);
CREATE  INDEX i_identifier ON applicationsettings(identifier);

ALTER TABLE users
ADD COLUMN type TINYINT unsigned DEFAULT 0 AFTER password,
ADD COLUMN dateCreated DATE NOT NULL,
ADD COLUMN lastUpdated DATE;