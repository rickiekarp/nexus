create table application(
   id 		      int auto_increment PRIMARY KEY,
   identifier     char(50)    NOT NULL,
   version        int         NOT NULL,
   updateenable   bool
);

insert into application (identifier, version, updateenable) VALUES ('appupdater', '1706111358', true);
insert into application (identifier, version, updateenable) VALUES ('botmanager', '1706111358', true);