delimiter //

create procedure createPermission($name varchar(50))
begin
    insert into permissions (permissionname) values ($name);
end //

create procedure createRole($name varchar(50))
begin
    insert into roles (rolename) values ($name);
end //

create procedure createRoleHasPermission($roleid smallint, $permname varchar(50))
begin
    declare _permid int;
    select id from permissions where permissionname = $permname into _permid;
    insert into role_permissions (roleid, permissionid) values ($roleid, _permid);
end //

create procedure createUser($name varchar(50), $password varchar(50))
begin
    insert into credentials (username, password, enabled, token) values ($name, $password, 1, null);
end //

create procedure createUserHasRole($user_id int, $role_id smallint)
begin
    insert into user_roles (userid, roleid) values ($user_id, $role_id);
end //

delimiter ;
