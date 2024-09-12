-- Create role
call createRole('ROLE_TOOLUSER');

-- Drop user_roles table
DROP TABLE IF EXISTS user_roles;

-- Drop password reminder columns
ALTER TABLE `login`
  DROP COLUMN lostPasswordHash,
  DROP COLUMN lostPasswordDate;

-- Add token_id column
ALTER TABLE users
ADD COLUMN token_id int unsigned NULL DEFAULT NULL AFTER password;

-- Drop and recreate createUser() procedure
DROP PROCEDURE IF EXISTS createUser;
CREATE PROCEDURE createUser(
    IN $name varchar(50), 
    IN $password varchar(50), 
    IN $usertype tinyint(3), 
    IN $enabled boolean)
BEGIN
    INSERT INTO users (username, password, type, enabled, dateCreated) values ($name, $password, $usertype, $enabled, now());
    INSERT INTO token (user_id)
        SELECT LAST_INSERT_ID();

    -- update token_id for new user
    UPDATE users
    SET token_id = (SELECT t.id FROM token t JOIN users u on u.id = t.user_id WHERE u.username = $name)
    WHERE username = $name;
END ;

-- Create Token table
DROP TABLE IF EXISTS token;
CREATE TABLE token(
   id 		          int unsigned      auto_increment   PRIMARY KEY,
   user_id          int unsigned      NOT NULL UNIQUE,
   access_token     varchar(50)       NULL default NULL,
   refresh_token    varchar(50)       NULL default NULL,
   expiry           TIMESTAMP         NULL default NULL,
   CONSTRAINT token_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE INDEX i_accesstoken ON token(access_token);