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
insert into posts (id,is_active,moderation_id,user_id,time,title,text,view_count) VALUES (1,1,"NEW",null,1,0,"Как создать проект","Смотрите во второй части ->",0)