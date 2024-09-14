-- Create role
call createRole('ROLE_GHOST');

-- Change column name
ALTER TABLE users CHANGE type role_id tinyint(3) unsigned NOT NULL DEFAULT 4;