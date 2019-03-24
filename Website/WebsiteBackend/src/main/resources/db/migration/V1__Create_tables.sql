CREATE TABLE skill(
   skill_id 		    int(11)         auto_increment   PRIMARY KEY,
   text         varchar(50)     NOT NULL UNIQUE,
   active       boolean         NOT NULL default 0
);

CREATE TABLE contact(
   contact_id 		    int(11)         auto_increment   PRIMARY KEY,
   name         varchar(50)     NOT NULL,
   email        varchar(50)     NOT NULL
);

CREATE TABLE company(
   company_id int(11) auto_increment PRIMARY KEY,
   name varchar(255) NOT NULL,
   type ENUM ('experience','education') NOT NULL
);

CREATE TABLE job(
   job_id int(11) auto_increment PRIMARY KEY,
   title varchar(255) NOT NULL
);

CREATE TABLE experience (
    experience_id int(11) auto_increment PRIMARY KEY,
    startDate DATE NOT NULL,
    endDate DATE,
    companyid int(11) NOT NULL,
    jobid int(11) NOT NULL,
    description TEXT,
    FOREIGN KEY (companyid) REFERENCES  company(company_id),
    FOREIGN KEY (jobid) REFERENCES  job(job_id)
);