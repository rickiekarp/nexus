-- Make index key unique
ALTER TABLE vault 
DROP KEY i_identifier, 
ADD UNIQUE KEY i_identifier (identifier);