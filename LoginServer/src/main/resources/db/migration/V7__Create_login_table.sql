-- General migration
ALTER TABLE users
ADD COLUMN email varchar(255) UNIQUE AFTER username,
MODIFY COLUMN dateCreated TIMESTAMP,
MODIFY COLUMN lastUpdated TIMESTAMP;

-- Login table
CREATE TABLE login(
   id 		        int unsigned     auto_increment   PRIMARY KEY,
   users_id         int unsigned     NOT NULL UNIQUE,
   lastLoginDate    TIMESTAMP,
   lastLoginIP      VARBINARY(16),
   lostPasswordDate TIMESTAMP,
   lostPasswordHash varchar(50),
   CONSTRAINT login_users_id_fk FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE  INDEX i_usersid ON login(users_id);

-- Drop and recreate createUser() procedure (again)
DROP PROCEDURE createUser;

delimiter //

CREATE PROCEDURE createUser(IN $name varchar(50), IN $password varchar(50), IN $role_id int unsigned, IN $enabled boolean)
BEGIN
    INSERT INTO users (username, password, enabled, dateCreated) values ($name, $password, $enabled, now());
    INSERT INTO user_roles (users_id, roles_id)
        SELECT LAST_INSERT_ID(), $role_id;
    INSERT INTO login (users_id)
        SELECT LAST_INSERT_ID();
END //

delimiter ;

-- Add all missing users to login table that have been created before this migration
INSERT INTO login (users_id) SELECT id FROM users WHERE id NOT IN (SELECT users_id FROM login);