CREATE TABLE tracking_weight (
  id INT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  users_id INT unsigned NOT NULL,
  dateAdded TIMESTAMP NOT NULL,
  weight TINYINT unsigned NOT NULL,
  description VARCHAR(255)
);
ALTER TABLE tracking_weight
ADD CONSTRAINT tracking_weight_users_id_fk FOREIGN KEY (users_id) REFERENCES login.users (id);

CREATE TABLE tracking_todo (
  id INT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  users_id INT unsigned NOT NULL,
  dateAdded TIMESTAMP NOT NULL,
  description VARCHAR(255),
  isDeleted boolean default false,
  lastUpdated TIMESTAMP NULL default NULL
);
ALTER TABLE tracking_todo
ADD CONSTRAINT tracking_todo_users_id_fk FOREIGN KEY (users_id) REFERENCES login.users (id);