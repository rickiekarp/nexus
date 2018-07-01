-- Create permissions
SELECT createPermission('CTRL_USER_LIST_GET');
SELECT createPermission('CTRL_USER_ADD_POST');
SELECT createPermission('CTRL_USER_EDIT_GET');
SELECT createPermission('CTRL_USER_EDIT_POST');
SELECT createPermission('CTRL_USER_DELETE_GET');

SELECT createPermission('CTRL_ROLE_LIST_GET');
SELECT createPermission('CTRL_ROLE_ADD_POST');
SELECT createPermission('CTRL_ROLE_EDIT_GET');
SELECT createPermission('CTRL_ROLE_EDIT_POST');
SELECT createPermission('CTRL_ROLE_DELETE_GET');

SELECT createPermission('CTRL_PERM_LIST_GET');
SELECT createPermission('CTRL_PERM_ADD_POST');
SELECT createPermission('CTRL_PERM_EDIT_GET');
SELECT createPermission('CTRL_PERM_EDIT_POST');
SELECT createPermission('CTRL_PERM_DELETE_GET');


-- Create roles
SELECT createRole('ROLE_ADMIN');
SELECT createRole('ROLE_USER');

SELECT createRoleHasPermission(1, 1);
SELECT createRoleHasPermission(1, 2);
SELECT createRoleHasPermission(1, 3);
SELECT createRoleHasPermission(1, 4);
SELECT createRoleHasPermission(1, 5);

SELECT createRoleHasPermission(1, 6);
SELECT createRoleHasPermission(1, 7);
SELECT createRoleHasPermission(1, 8);
SELECT createRoleHasPermission(1, 9);
SELECT createRoleHasPermission(1, 10);

SELECT createRoleHasPermission(1, 11);
SELECT createRoleHasPermission(1, 12);
SELECT createRoleHasPermission(1, 13);
SELECT createRoleHasPermission(1, 14);
SELECT createRoleHasPermission(1, 15);


-- Create accounts
SELECT createUser('admin', 'admin', null);
SELECT createUserHasRole(1, 1);

SELECT createUser('tokentest', 'test', null);
SELECT createUserHasRole(2, 2);

SELECT createUser('logintest', 'test', 'MzpScFBseFVFQ2JKbWhHREli');
SELECT createUserHasRole(3, 2);

SELECT createUser('user', 'test', null);
SELECT createUserHasRole(4, 2);
