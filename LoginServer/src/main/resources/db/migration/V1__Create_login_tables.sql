--SET search_path TO login;

-- Set up tables
CREATE TABLE CREDENTIALS(
   ID 		   serial       PRIMARY KEY,
   USERNAME    CHAR(50)     NOT NULL,
   PASSWORD    CHAR(255)    NOT NULL,
   ENABLED     boolean      NOT NULL,
   TOKEN       CHAR(50)
);

CREATE TABLE ROLES(
    ID        serial    PRIMARY KEY,
    ROLENAME CHAR(50)   NOT NULL
);
create unique index roles_unique_idx on roles (rolename);

CREATE TABLE USER_ROLES(
    USERID INTEGER NOT NULL,
    ROLEID INTEGER NOT NULL,
    CONSTRAINT credentials FOREIGN KEY (USERID) REFERENCES credentials (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT role FOREIGN KEY (ROLEID) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PERMISSIONS(
   ID 		   serial       PRIMARY KEY,
   PERMISSIONNAME CHAR(50)  NOT NULL
);
create unique index permissions_unique_idx on permissions (permissionname);

CREATE TABLE ROLE_PERMISSIONS(
    ROLEID       INTEGER NOT NULL,
    PERMISSIONID INTEGER NOT NULL,
    FOREIGN KEY (ROLEID) REFERENCES roles (ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (PERMISSIONID) REFERENCES permissions (ID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Add stored procedures
CREATE OR REPLACE FUNCTION createPermission(name VARCHAR(50))
    RETURNS void AS $$
    BEGIN
      INSERT INTO permissions (permissionname) VALUES (name);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION createRole(name VARCHAR(50))
    RETURNS void AS $$
    BEGIN
      INSERT INTO roles (rolename) VALUES (name);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION createRoleHasPermission(roleid int, permissionid int)
    RETURNS void AS $$
    BEGIN
        INSERT INTO role_permissions (roleid, permissionid) values (roleid, permissionid);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION createUser(name varchar(50), password varchar(50), token varchar(50))
    RETURNS void AS $$
    BEGIN
      INSERT INTO credentials (username, password, enabled, token) values (name, password, true, token);
    END;
    $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION createUserHasRole(userid int, roleid int)
    RETURNS void AS $$
    BEGIN
      INSERT INTO user_roles (userid, roleid) VALUES (userid, roleid);
    END;
    $$ LANGUAGE plpgsql;