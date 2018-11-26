-- Create permissions
call createPermission('CTRL_USER_LIST_GET');
call createPermission('CTRL_USER_ADD_POST');
call createPermission('CTRL_USER_EDIT_GET');
call createPermission('CTRL_USER_EDIT_POST');
call createPermission('CTRL_USER_DELETE_GET');

call createPermission('CTRL_ROLE_LIST_GET');
call createPermission('CTRL_ROLE_ADD_POST');
call createPermission('CTRL_ROLE_EDIT_GET');
call createPermission('CTRL_ROLE_EDIT_POST');
call createPermission('CTRL_ROLE_DELETE_GET');

call createPermission('CTRL_PERM_LIST_GET');
call createPermission('CTRL_PERM_ADD_POST');
call createPermission('CTRL_PERM_EDIT_GET');
call createPermission('CTRL_PERM_EDIT_POST');
call createPermission('CTRL_PERM_DELETE_GET');

-- Create roles
call createRole('ROLE_ADMIN');
call createRole('ROLE_USER');

-- Create permissions per role
call createRoleHasPermission(1, 'CTRL_USER_LIST_GET');
call createRoleHasPermission(1, 'CTRL_USER_ADD_POST');
call createRoleHasPermission(1, 'CTRL_USER_EDIT_GET');
call createRoleHasPermission(1, 'CTRL_USER_EDIT_POST');
call createRoleHasPermission(1, 'CTRL_USER_DELETE_GET');

call createRoleHasPermission(1, 'CTRL_ROLE_LIST_GET');
call createRoleHasPermission(1, 'CTRL_ROLE_ADD_POST');
call createRoleHasPermission(1, 'CTRL_ROLE_EDIT_GET');
call createRoleHasPermission(1, 'CTRL_ROLE_EDIT_POST');
call createRoleHasPermission(1, 'CTRL_ROLE_DELETE_GET');

call createRoleHasPermission(1, 'CTRL_PERM_LIST_GET');
call createRoleHasPermission(1, 'CTRL_PERM_ADD_POST');
call createRoleHasPermission(1, 'CTRL_PERM_EDIT_GET');
call createRoleHasPermission(1, 'CTRL_PERM_EDIT_POST');
call createRoleHasPermission(1, 'CTRL_PERM_DELETE_GET');


-- Create accounts
call createUser('admin', 'admin');
call createUserHasRole(1, 1);

call createUser('logintest', 'test');
call createUserHasRole(2, 2);

call createUser('user', 'test');
call createUserHasRole(3, 2);
