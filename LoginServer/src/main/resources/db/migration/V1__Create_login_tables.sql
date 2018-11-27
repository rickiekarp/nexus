CREATE TABLE credentials(
   id 		   int(11)          auto_increment   PRIMARY KEY,
   username    varchar(50)      NOT NULL UNIQUE,
   password    varchar(255)     NOT NULL,
   enabled     bool             NOT NULL,
   token       varchar(50)
);
CREATE TABLE roles(
    id        int(11) auto_increment    PRIMARY KEY,
    rolename varchar(50)   NOT NULL
);
CREATE TABLE user_roles(
    userid INTEGER NOT NULL,
    roleid INTEGER NOT NULL,
    CONSTRAINT credentials FOREIGN KEY (userid) REFERENCES credentials (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT role FOREIGN KEY (roleid) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE permissions(
   id 		   int       auto_increment PRIMARY KEY,
   permissionname varchar(50)  NOT NULL
);
create unique index permissions_unique_idx on permissions (permissionname);

CREATE TABLE role_permissions(
    roleid       int NOT NULL,
    permissionid int NOT NULL,
    FOREIGN KEY (roleid) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (permissionid) REFERENCES permissions (id) ON DELETE CASCADE ON UPDATE CASCADE
);