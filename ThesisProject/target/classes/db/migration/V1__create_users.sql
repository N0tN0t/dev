drop table if exists users;
create table users (
  id integer NOT NULL,
  code varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  is_moderator boolean NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  photo varchar(255) NOT NULL,
  reg_time timestamp NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);