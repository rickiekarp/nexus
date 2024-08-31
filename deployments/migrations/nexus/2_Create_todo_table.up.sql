CREATE TABLE reminders (
  id INT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  users_id INT unsigned NOT NULL,
  dateAdded TIMESTAMP NOT NULL,
  description VARCHAR(255),
  isDeleted boolean default false,
  lastUpdated TIMESTAMP NULL default NULL
);
ALTER TABLE reminders
ADD CONSTRAINT reminders_users_id_fk FOREIGN KEY (users_id) REFERENCES login.users (id);