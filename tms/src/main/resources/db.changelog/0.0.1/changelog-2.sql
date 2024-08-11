--liquibase formatted sql

--changeset Koss:2
--comment add new table
create table demo2
(
    id integer,
    title varchar(32)
);

insert into demo2
values (1, 'First title2'),
       (2, 'Second title2');
--rollback truncate table demo2;