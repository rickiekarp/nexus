-- General migration

ALTER TABLE applicationsettings
MODIFY COLUMN lastUpdated TIMESTAMP;

-- Create AdminIp table
CREATE TABLE adminip(
   id 		        int unsigned     auto_increment   PRIMARY KEY,
   ipAddress        VARBINARY(16)    NOT NULL UNIQUE
);

