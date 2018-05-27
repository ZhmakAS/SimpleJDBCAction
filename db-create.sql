drop table if exists users;
CREATE table users(
   id INT NOT NULL AUTO_INCREMENT,
   login VARCHAR(55) NOT NULL,
   PRIMARY KEY ( id )
);

drop table if exists groups;
CREATE table groups(
   id INT NOT NULL AUTO_INCREMENT,
   name VARCHAR(55) NOT NULL,
   PRIMARY KEY ( id )
);

drop table if exists users_groups;
CREATE table users_groups(
   user_id INT NOT NULL,
   group_id INT NOT NULL,
   PRIMARY KEY ( user_id,group_id),
   FOREIGN KEY (user_id) REFERENCES users(id),
   FOREIGN KEY (group_id) REFERENCES groups(id)
   ON DELETE CASCADE
   ON UPDATE CASCADE
);

insert into users (login) values ('ivanov');
insert into groups (name) values ('teamA');
