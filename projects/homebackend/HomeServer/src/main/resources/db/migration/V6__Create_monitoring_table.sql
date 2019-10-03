CREATE TABLE monitoring_system (
  id INT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  dateAdded TIMESTAMP NOT NULL,
  temperature FLOAT(5, 2) unsigned NOT NULL,
  isWarning boolean default false,
  description VARCHAR(255)
);

ALTER TABLE `monitoring_system` ADD INDEX `idx_dateAdded` (`dateAdded`);