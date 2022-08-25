insert into posts (id,is_active,moderation_status,moderation_id,user_id,time,title,text,view_count) VALUES (1,1,'ACCEPTED',null,1,CURRENT_DATE,'Как создать проект','Смотрите во второй части ->',0);
INSERT INTO global_settings(code, name, value)
VALUES ('MULTIUSER_MODE', 'Многопользовательский режим', 'YES');
INSERT INTO global_settings(code, name, value)
VALUES ('POST_PREMODERATION', 'Премодерация постов', 'YES');
INSERT INTO global_settings(code, name, value)
VALUES ('STATISTICS_IS_PUBLIC', 'Показывать всем статистику блога ', 'YES');