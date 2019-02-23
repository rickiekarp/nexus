CREATE TABLE project(
   id 		    int(11)         auto_increment   PRIMARY KEY,
   identifier   varchar(50)     NOT NULL UNIQUE,
   type         int             NOT NULL
);

CREATE TABLE skill(
   id 		    int(11)         auto_increment   PRIMARY KEY,
   text         varchar(50)     NOT NULL UNIQUE,
   active       boolean         NOT NULL default 0
);

CREATE TABLE contact(
   id 		    int(11)         auto_increment   PRIMARY KEY,
   name         varchar(50)     NOT NULL,
   email        varchar(50)     NOT NULL
);