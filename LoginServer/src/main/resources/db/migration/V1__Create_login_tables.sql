CREATE TABLE users(
   id 		   int unsigned     auto_increment   PRIMARY KEY,
   username    varchar(50)      NOT NULL UNIQUE,
   password    varchar(255)     NOT NULL,
   enabled     bool default false NOT NULL,
   token       varchar(50)
);
CREATE  INDEX i_token ON users(token);

CREATE TABLE roles(
    id        int unsigned auto_increment    PRIMARY KEY,
    name varchar(50)   NOT NULL
);
CREATE TABLE user_roles(
    users_id int unsigned NOT NULL,
    roles_id int unsigned NOT NULL,
    CONSTRAINT users_id_fk FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT roles_id_fk FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE permissions(
   id 	int unsigned auto_increment PRIMARY KEY,
   name varchar(50)  NOT NULL
);
create unique index permissions_unique_idx on permissions (name);

CREATE TABLE role_permissions(
    role_id       int unsigned NOT NULL,
    permission_id int unsigned NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE ON UPDATE CASCADE
);