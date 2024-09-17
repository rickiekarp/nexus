-- Update filelist table
ALTER TABLE filelist
ADD COLUMN owner varchar(50) NULL DEFAULT NULL AFTER checksum,
ADD COLUMN lastupdate int unsigned NULL DEFAULT UNIX_TIMESTAMP() AFTER inserttime;

-- Drop and recreate updateFileIterationInStorage() procedure
DROP PROCEDURE IF EXISTS updateFileIterationInStorage;
CREATE PROCEDURE updateFileIterationInStorage(
    IN $iteration varchar(100), 
    IN $fileid bigint unsigned)
BEGIN
    UPDATE filelist_additional_data set `value` = $iteration WHERE file_id = $fileid AND property = "iteration";
    UPDATE filelist set lastupdate = UNIX_TIMESTAMP() WHERE id = $fileid;
END ;