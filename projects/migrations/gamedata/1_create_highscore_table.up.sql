create table highscore(
   id 		        int unsigned auto_increment PRIMARY KEY,
   name             char(50)     NOT NULL,
   points           int unsigned DEFAULT 0,
   dateAdded        timestamp    DEFAULT CURRENT_TIMESTAMP
);