-- General migration

ALTER TABLE shopping_note
MODIFY COLUMN dateAdded TIMESTAMP,
MODIFY COLUMN dateBought TIMESTAMP NULL default NULL,
MODIFY COLUMN lastUpdated TIMESTAMP NULL default NULL;

-- Create applicationsettings table
CREATE TABLE applicationsettings(
   id 		        int unsigned    auto_increment   PRIMARY KEY,
   identifier       varchar(50)     NOT NULL,
   content          TEXT            NOT NULL,
   lastUpdated      TIMESTAMP       NULL default NULL
);
CREATE  INDEX i_identifier ON applicationsettings(identifier);