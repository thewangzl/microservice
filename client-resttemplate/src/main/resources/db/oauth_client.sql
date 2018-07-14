create database clientdb;
create user 'testuser'@'localhost' identified by 'test';
grant all privileges on clientdb.* to 'testuser'@'localhost' identified by 'test';
flush privileges;

use clientdb;

create table client_user(
	id bigint auto_increment primary key,
	username varchar(100),
	password varchar(100),
	access_token varchar(100) NULL,
	access_token_validity datetime NULL,
	refresh_token varchar(100) NULL
);

insert into client_user (username, password) value('thewangzl','$2a$10$MEhvO9VdIgdyuFx/iZg/dONNg3JmBvNujel1WykNjNNpWfPDA.rXG');



