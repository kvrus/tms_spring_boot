--liquibase formatted sql

--changeset Koss:3
--comment add roles

INSERT INTO public.roles ("name") VALUES('SUPERUSER');
INSERT INTO public.roles ("name") VALUES('USER');