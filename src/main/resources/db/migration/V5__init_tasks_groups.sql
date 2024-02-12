create table task_groups(
       id int primary key auto_increment,
       description VARCHAR(100),
       done bit
);
alter table tasks add column task_group_id int null;
alter table tasks add constraint task_group_FK foreign key(task_group_id) references task_groups(id);
