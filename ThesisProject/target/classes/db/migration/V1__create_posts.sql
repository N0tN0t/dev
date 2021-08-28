drop table if exists posts;
create table posts (
   id SERIAL NOT NULL,
   is_active integer NOT NULL,
   moderation_status varchar(255),
   moderation_id integer,
   user_id int4 NOT NULL,
   time timestamp without time zone NOT NULL,
   title varchar(255) NOT NULL,
   text text NOT NULL,
   view_count int4 NOT NULL,
   CONSTRAINT posts_pkey PRIMARY KEY (id)
);

drop table if exists users;
create table users (
  id SERIAL NOT NULL,
  code varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  is_moderator boolean NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  photo varchar(255) NOT NULL,
  reg_time timestamp NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id)
);

drop table if exists post_votes;
create table post_votes (
  id SERIAL NOT NULL,
  user_id integer NOT NULL,
  post_id integer NOT NULL,
  time timestamp without time zone NOT NULL,
  value integer NOT NULL
);

drop table if exists tags;
create table tags (
  id SERIAL NOT NULL,
  name varchar(255) NOT NULL
);

drop table if exists tag2post;
create table tag2post (
  id SERIAL NOT NULL,
  parent_id integer,
  post_id integer NOT NULL,
  user_id integer NOT NULL,
  time timestamp without time zone NOT NULL,
  text TEXT NOT NULL
);

drop table if exists captcha_codes;
create table captcha_codes (
  id SERIAL NOT NULL,
  time timestamp without time zone NOT NULL,
  code varchar(255) NOT NULL,
  secret_code varchar(255) NOT NULL
);

drop table if exists global_settings;
create table global_settings (
  id SERIAL NOT NULL,
  code varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  value varchar(255) NOT NULL
);