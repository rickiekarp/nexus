-- Set up tables
CREATE TABLE user (
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username   VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    enabled    TINYINT(1)  NOT NULL,
    token      VARCHAR(50),
    PRIMARY KEY (id),
    UNIQUE INDEX username (username)
)
COLLATE='utf8_general_ci' ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE roles (
    id        INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
    rolename  VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX rolename (rolename)
)
COLLATE='utf8_general_ci' ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE user_roles (
    user_id int(6) UNSIGNED NOT NULL,
    role_id int(6) UNSIGNED NOT NULL,
    KEY USER (user_id),
    KEY role (role_id),
    CONSTRAINT USER FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
)
COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE permissions (
    id             INT(6) UNSIGNED NOT NULL AUTO_INCREMENT,
    permissionname VARCHAR(50) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE INDEX permissionname (permissionname)
)
COLLATE='utf8_general_ci' ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE role_permissions (
    role_id       INT(6) UNSIGNED NOT NULL,
    permission_id INT(6) UNSIGNED NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE ON UPDATE CASCADE
)
COLLATE='utf8_general_ci' ENGINE=InnoDB;

-- Add stored procedures
delimiter //

create procedure createPermission($name varchar(50))
begin
    insert into permissions (permissionname) values ($name);
end //

create procedure createRole($name varchar(50), out $id int)
begin
    insert into roles (rolename) values ($name);
    set $id := last_insert_id();
end //

create procedure createRoleHasPermission($role_id smallint, $perm_name varchar(50))
begin
    declare _perm_id int;
    select id from permissions where permissionname = $perm_name into _perm_id;
    insert into role_permissions (role_id, permission_id) values ($role_id, _perm_id);
end //

create procedure createUser($name varchar(50), $password varchar(50), $token varchar(50), out $id int)
begin
    insert into user (username, password, enabled, token) values ($name, $password, 1, $token);
    set $id := last_insert_id();
end //

create procedure createUserHasRole($user_id int, $role_id smallint)
begin
    insert into user_roles (user_id, role_id) values ($user_id, $role_id);
end //

delimiter ;
