drop table if exists tasks;
create table tasks(
                      id int primary key auto_increment,
                      description VARCHAR(100),
                      done bit
);
