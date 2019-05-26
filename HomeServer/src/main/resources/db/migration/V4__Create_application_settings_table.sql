-- General migration

ALTER TABLE shopping_note
MODIFY COLUMN dateAdded TIMESTAMP,
MODIFY COLUMN dateBought TIMESTAMP,
MODIFY COLUMN lastUpdated TIMESTAMP;

-- Create AdminIp table
CREATE TABLE applicationsettings(
   id 		        int unsigned    auto_increment   PRIMARY KEY,
   identifier       varchar(50)     NOT NULL,
   content          TEXT            NOT NULL,
   lastUpdated      TIMESTAMP
);
CREATE  INDEX i_identifier ON applicationsettings(identifier);