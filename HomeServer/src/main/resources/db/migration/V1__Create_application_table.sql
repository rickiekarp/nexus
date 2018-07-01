--SET search_path TO home;

CREATE TABLE APPLICATION(
   ID 		      serial PRIMARY KEY,
   IDENTIFIER     CHAR(50)    NOT NULL,
   VERSION        INT         NOT NULL,
   UPDATEENABLE   boolean
);

INSERT INTO application (identifier, version, updateEnable) VALUES ('appupdater', '1706111358', 'true');
INSERT INTO application (identifier, version, updateEnable) VALUES ('botmanager', '1706111358', 'true');
