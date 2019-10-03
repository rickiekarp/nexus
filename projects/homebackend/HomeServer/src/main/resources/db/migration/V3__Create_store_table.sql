ALTER TABLE shopping_note
ADD COLUMN price NUMERIC(10,2) AFTER description,
ADD COLUMN dateBought DATE AFTER dateAdded,
ADD COLUMN store_id INT unsigned AFTER dateBought;

CREATE TABLE shopping_store (
  id INT unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) UNIQUE
);

ALTER TABLE shopping_note
ADD CONSTRAINT store_id_fk FOREIGN KEY (store_id) REFERENCES data_home.shopping_store (id);