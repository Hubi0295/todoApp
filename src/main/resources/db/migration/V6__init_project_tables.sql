create table projects(
       id int primary key auto_increment,
       description VARCHAR(100) not null
);
create table project_steps(
        id int primary key auto_increment,
        description VARCHAR(100) not null,
        days_to_deadline int not null,
        project_id int not null,
        CONSTRAINT project_id_FK FOREIGN KEY(project_id) REFERENCES projects(id)
);
alter table task_groups add column project_id int null;
alter table task_groups add constraint taskgroup_project_FK foreign key(project_id) references projects(id);