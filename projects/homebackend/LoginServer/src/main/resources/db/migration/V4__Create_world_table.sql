CREATE TABLE world(
   id 		        int unsigned     auto_increment   PRIMARY KEY,
   name             varchar(50)      NOT NULL,
   url              varchar(255)     NOT NULL,
   worldstatus_id   TINYINT unsigned NOT NULL DEFAULT 1
);

CREATE TABLE worldstatus(
   id 		        TINYINT unsigned auto_increment   PRIMARY KEY,
   description      varchar(50)      NOT NULL
);

ALTER TABLE world
ADD CONSTRAINT worldstatus_id_fk FOREIGN KEY (worldstatus_id) REFERENCES login.worldstatus (id);

insert into worldstatus (description) VALUES ('Online');
insert into worldstatus (description) VALUES ('Offline');
insert into worldstatus (description) VALUES ('Maintenance');