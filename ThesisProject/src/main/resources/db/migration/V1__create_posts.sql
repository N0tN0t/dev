drop table if exists posts;
create table posts (
    id integer not null auto_increment,
    is_active integer,
    moderation_status ENUM("NEW","ACCEPTED","DECLINED") not null,
    moderation_id integer,
    user_id integer,
    time date,
    title varchar(255),
    text varchar(255),
    view_count integer,
    primary key (id)
);