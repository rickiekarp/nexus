-- Make index key unique
ALTER TABLE vault
ADD COLUMN `type` varchar(50) AFTER `token`,
ADD COLUMN `description` varchar(255) AFTER `createdAt`;