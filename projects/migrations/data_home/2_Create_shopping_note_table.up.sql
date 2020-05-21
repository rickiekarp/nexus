CREATE TABLE shopping_note (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50),
  description VARCHAR(255),
  users_id INT unsigned NOT NULL,
  dateAdded DATE NOT NULL,
  lastUpdated DATE,
  isDeleted boolean default false
);

ALTER TABLE shopping_note
ADD CONSTRAINT users_id_fk FOREIGN KEY (users_id) REFERENCES login.users (id);