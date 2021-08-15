drop table if exists posts;
create table posts (
   id int4 NOT NULL,
   is_active boolean NOT NULL,
   moderation_status varchar(255),
   moderation_id integer NOT NULL,
   user_id int4 NOT NULL,
   time timestamp NOT NULL,
   title varchar(255) NOT NULL,
   text text NOT NULL,
   view_count int4 NOT NULL,
   CONSTRAINT posts_pkey PRIMARY KEY (id)
);