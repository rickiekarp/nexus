-- Add filehash column
ALTER TABLE filelist
ADD COLUMN filehash varchar(255) NULL DEFAULT NULL AFTER mtime;

CREATE INDEX idx_filehash ON filelist(filehash);

-- Drop and recreate insertFileToStorage() procedure
DROP PROCEDURE IF EXISTS insertFileToStorage;
CREATE PROCEDURE insertFileToStorage(
    IN $path varchar(4000), 
    IN $name varchar(255), 
    IN $size bigint unsigned, 
    IN $mtime int unsigned, 
    IN $filehash varchar(255),
    IN $checksum varchar(255),
    IN $owner varchar(50))
BEGIN
    DECLARE fileid BIGINT unsigned DEFAULT 0;

    SET @fileOwner = nullif($owner, '');

    INSERT INTO filelist (path, name, size, mtime, filehash, checksum, owner) 
        VALUES ($path, $name, $size, $mtime, $filehash, $checksum, @fileOwner);
    SELECT LAST_INSERT_ID()	INTO fileid;

    INSERT INTO filelist_additional_data (file_id, property, value) values (fileid, "iteration", "1");
END ;
