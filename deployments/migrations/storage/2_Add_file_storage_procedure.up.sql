-- Drop and recreate insertFileToStorage() procedure
DROP PROCEDURE IF EXISTS insertFileToStorage;
CREATE PROCEDURE insertFileToStorage(
    IN $path varchar(4000), 
    IN $name varchar(255), 
    IN $size bigint unsigned, 
    IN $mtime int unsigned, 
    IN $checksum varchar(255))
BEGIN
    DECLARE fileid BIGINT unsigned DEFAULT 0;

    INSERT INTO filelist (path, name, size, mtime, checksum) values ($path, $name, $size, $mtime, $checksum);
    SELECT LAST_INSERT_ID()	INTO fileid;

    INSERT INTO filelist_additional_data (file_id, property, value) values (fileid, "iteration", "1");
END ;
