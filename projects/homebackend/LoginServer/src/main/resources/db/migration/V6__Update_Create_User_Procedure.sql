-- General migration
ALTER TABLE users MODIFY COLUMN type TINYINT unsigned DEFAULT 0 NOT NULL;
ALTER TABLE user_roles MODIFY COLUMN users_id int unsigned NOT NULL UNIQUE;

-- Drop and recreate createUser() procedure
DROP PROCEDURE createUser;
DROP PROCEDURE createUserHasRole;

delimiter //

CREATE PROCEDURE createUser(IN $name varchar(50), IN $password varchar(50), IN $role_id int unsigned, IN $enabled boolean)
BEGIN
    INSERT INTO users (username, password, enabled, dateCreated) values ($name, $password, $enabled, now());
    INSERT INTO user_roles (users_id, roles_id)
        SELECT LAST_INSERT_ID(), $role_id;
END //

delimiter ;