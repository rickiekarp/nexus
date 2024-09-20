-- fileguardian table
CREATE TABLE fileguardian(
   id 		        bigint unsigned  auto_increment   PRIMARY KEY,
   type             varchar(50)    NOT NULL,
   source           varchar(255)   NOT NULL,
   target           varchar(255)   NOT NULL,
   context          varchar(50)    NOT NULL,
   inserttime       int unsigned   NOT NULL DEFAULT UNIX_TIMESTAMP(),
   CONSTRAINT `u_source` UNIQUE(`source`, `context`),
   CONSTRAINT `u_target` UNIQUE(`target`, `context`)
);
