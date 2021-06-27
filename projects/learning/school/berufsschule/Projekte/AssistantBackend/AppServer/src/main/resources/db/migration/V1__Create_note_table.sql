CREATE TABLE note (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50),
  content VARCHAR(255),
  userid INT UNSIGNED NOT NULL,
  dateAdded DATE NOT NULL,
  isDeleted BIT(1) DEFAULT 0
);

ALTER TABLE note
ADD CONSTRAINT userid FOREIGN KEY (userid) REFERENCES appdb_login.user (id);

INSERT INTO note (title, content, userid, dateAdded) VALUES ('testTitle', 'testnote', 1, "2017-01-01");
