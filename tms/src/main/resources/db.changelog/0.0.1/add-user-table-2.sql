--liquibase formatted sql

--changeset Koss:2
--comment add user tamles
create table roles
(
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    name varchar(255),
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

create table users
(
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    username varchar(255),
    password varchar(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

create table user_role
(
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    user_id int8,
    role_id int8,
    CONSTRAINT user_role_pkey PRIMARY KEY (id)
);

ALTER TABLE user_role ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_role ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES roles(id);

INSERT INTO public.roles ("name") VALUES('superuser');

--rollback truncate table demo2;