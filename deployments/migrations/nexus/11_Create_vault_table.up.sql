-- Create vault table
CREATE TABLE vault(
   id 		        int unsigned    auto_increment   PRIMARY KEY,
   identifier       varchar(50)     NOT NULL,
   token            varchar(50)     NOT NULL,
   validUntil       int unsigned       NULL default NULL,
   lastAccess       int unsigned       NULL default NULL,
   createdAt        int unsigned       NULL default UNIX_TIMESTAMP()
);
CREATE INDEX i_identifier ON vault(identifier);