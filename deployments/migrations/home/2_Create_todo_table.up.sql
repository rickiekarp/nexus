CREATE TABLE todo (
  id INT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  users_id INT unsigned NOT NULL,
  dateAdded TIMESTAMP NOT NULL,
  description VARCHAR(255),
  isDeleted boolean default false,
  lastUpdated TIMESTAMP NULL default NULL
);
ALTER TABLE todo
ADD CONSTRAINT todo_users_id_fk FOREIGN KEY (users_id) REFERENCES login.users (id);