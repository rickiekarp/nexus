-- Filelist table
CREATE TABLE filelist(
   id 		        bigint unsigned  auto_increment   PRIMARY KEY,
   path             varchar(4000)    NULL default NULL,
   name             varchar(255)     NOT NULL,
   size             bigint unsigned  NOT NULL DEFAULT 0,
   mtime            int unsigned     NOT NULL DEFAULT 0,
   checksum         varchar(255)     NOT NULL,
   inserttime       int unsigned     NOT NULL DEFAULT UNIX_TIMESTAMP(),
   CONSTRAINT `u_checksum` UNIQUE(`checksum`)
);
CREATE INDEX idx_name ON filelist(name);
CREATE INDEX idx_checksum ON filelist(checksum);

-- Filelist additional data table
CREATE TABLE filelist_additional_data(
   id 		       bigint unsigned  auto_increment   PRIMARY KEY,
   file_id 	    bigint unsigned      NOT NULL DEFAULT 0,
   property        varchar(100)         NULL default NULL,
   value           varchar(100)         NOT NULL,
   CONSTRAINT `u_properties` UNIQUE(`file_id`, `property`),
   CONSTRAINT file_id_fk FOREIGN KEY (file_id) REFERENCES filelist (id) ON DELETE CASCADE ON UPDATE CASCADE
);