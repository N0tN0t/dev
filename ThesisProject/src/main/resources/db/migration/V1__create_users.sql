drop table if exists users;
create table users (
    id integer not null auto_increment,
    is_moderator boolean,
    reg_time date,
    name varchar(255),
    email varchar(255),
    password varchar(255),
    code varchar(255),
    photo varchar(255),
    primary key (id)
);