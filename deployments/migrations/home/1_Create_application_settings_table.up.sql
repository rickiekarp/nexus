-- Create applicationsettings table
CREATE TABLE applicationsettings(
   id 		        int unsigned    auto_increment   PRIMARY KEY,
   identifier       varchar(50)     NOT NULL,
   content          TEXT            NOT NULL,
   lastUpdated      TIMESTAMP       NULL default NULL
);
CREATE  INDEX i_identifier ON applicationsettings(identifier);