-- Drop tokens from users table
ALTER TABLE token
  ADD COLUMN lastUpdated TIMESTAMP NULL DEFAULT NULL;