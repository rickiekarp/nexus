create procedure createPermission($name varchar(50))
begin
    insert into permissions (name) values ($name);
end ;

create procedure createRole($name varchar(50))
begin
    insert into roles (name) values ($name);
end ;

create procedure createRoleHasPermission($roleid smallint, $permname varchar(50))
begin
    declare _permid int;
    select id from permissions where name = $permname into _permid;
    insert into role_permissions (role_id, permission_id) values ($roleid, _permid);
end ;

create procedure createUser($name varchar(50), $password varchar(50), $enabled boolean)
begin
    insert into users (username, password, enabled, token) values ($name, $password, $enabled, null);
end ;

create procedure createUserHasRole($user_id int, $role_id smallint)
begin
    insert into user_roles (users_id, roles_id) values ($user_id, $role_id);
end ;
