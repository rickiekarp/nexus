-- Files table
CREATE TABLE files(
   id 		        bigint unsigned  auto_increment   PRIMARY KEY,
   path             varchar(4000)    NULL default NULL,
   name             varchar(255)     NOT NULL,
   size             bigint unsigned  NOT NULL DEFAULT 0,
   mtime            int unsigned     NOT NULL DEFAULT 0,
   checksum         varchar(255)     NOT NULL,
   inserttime       int unsigned     NOT NULL DEFAULT UNIX_TIMESTAMP(),
   CONSTRAINT `u_checksum` UNIQUE(`checksum`),
   CONSTRAINT `u_file_properties` UNIQUE(`size`, `mtime`)
);
CREATE INDEX idx_name ON files(name);
CREATE INDEX idx_checksum ON files(checksum);

-- Files table
CREATE TABLE files_additional_data(
   id 		       bigint unsigned  auto_increment   PRIMARY KEY,
   files_id 	    bigint unsigned      NOT NULL DEFAULT 0,
   property        varchar(100)         NULL default NULL,
   value           varchar(100)         NOT NULL,
   CONSTRAINT `u_properties` UNIQUE(`files_id`, `property`),
   CONSTRAINT files_fk FOREIGN KEY (files_id) REFERENCES files (id) ON DELETE CASCADE ON UPDATE CASCADE
);