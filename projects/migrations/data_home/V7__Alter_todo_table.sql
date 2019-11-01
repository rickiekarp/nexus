ALTER TABLE tracking_todo
ADD COLUMN reminder_interval TINYINT unsigned NOT NULL default 1 AFTER description,
ADD COLUMN reminder_senddate TIMESTAMP NULL default NULL AFTER reminder_interval,
ADD COLUMN reminder_enddate TIMESTAMP NULL default NULL AFTER reminder_senddate,
ADD INDEX `idx_reminderEnddate` (`reminder_enddate`);