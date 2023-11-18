-- CREATE location_city table and add data
create table location_city(
   id 		int auto_increment PRIMARY KEY,
   city     VARCHAR(255) NOT NULL
);
insert into location_city (city) VALUES ('Elmshorn');
insert into location_city (city) VALUES ('Hamburg');
insert into location_city (city) VALUES ('Karlsruhe');

-- CREATE location_country table and add data
create table location_country(
   id       int auto_increment PRIMARY KEY,
   country  VARCHAR(255) NOT NULL
);
insert into location_country (country) VALUES ('Germany');

-- CREATE junction table
create table location_junction(
   id 		   int auto_increment PRIMARY KEY,
   city_id     int NOT NULL,
   country_id  int NOT NULL
);
ALTER TABLE location_junction ADD CONSTRAINT city_id_fk FOREIGN KEY (city_id) REFERENCES data_home.location_city (id);
ALTER TABLE location_junction ADD CONSTRAINT country_id_fk FOREIGN KEY (country_id) REFERENCES data_home.location_country (id);
insert into location_junction (city_id, country_id) VALUES (1, 1);
insert into location_junction (city_id, country_id) VALUES (2, 1);
insert into location_junction (city_id, country_id) VALUES (3, 1);

-- ADD location to experience table
ALTER TABLE experience
ADD COLUMN location_id INT NOT NULL AFTER jobid;

update experience set location_id = 1 where experience_id in (1,2);
update experience set location_id = 2 where experience_id in (3,4,5,6);
update experience set location_id = 3 where experience_id in (7);