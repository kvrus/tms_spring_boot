--liquibase formatted sql

--changeset Koss:1
--comment first migration

CREATE TABLE test_case (
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    author_id int8,
    status_id int8,
    category_id int8,
    assignee_tester_id int8,
    priority_id int8,
    reviewer_tester_id int8,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_automated BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    description text,
    requirements text,
    name varchar(255),
    CONSTRAINT test_case_pkey PRIMARY KEY (id)
);

CREATE TABLE test_run (
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    author_id int8,
    plan_id int8,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    start_date    TIMESTAMP,
    finish_date TIMESTAMP,
    description   text,
    name          varchar(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT test_run_pkey PRIMARY KEY (id)
);

CREATE TABLE test_plan (
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    parent_id int8,
    author_id int8,
    plan_type_id int8,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    description text,
    name varchar(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT test_plan_pkey PRIMARY KEY (id)
);

CREATE TABLE test_execution (
    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    case_id int8,
    run_id int8,
    status_id int8,
    tester_id int8,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    start_date    TIMESTAMP,
    finish_date TIMESTAMP,
    description text,
    name varchar(255),
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT test_execution_pkey PRIMARY KEY (id)
);

CREATE TABLE test_plan_type (
                                id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                description varchar(255),
                                name varchar(255),
                                is_deleted BOOLEAN DEFAULT FALSE,
                                CONSTRAINT test_plan_type_pkey PRIMARY KEY (id)
);

CREATE TABLE test_case_status (
                                id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                description varchar(255),
                                name varchar(255),
                                is_deleted BOOLEAN DEFAULT FALSE,
                                CONSTRAINT test_case_status_pkey PRIMARY KEY (id)
);

CREATE TABLE test_case_category (
                                  id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                  description varchar(255),
                                  name varchar(255),
                                  is_deleted BOOLEAN DEFAULT FALSE,
                                  CONSTRAINT test_case_category_pkey PRIMARY KEY (id)
);

CREATE TABLE test_case_priority (
                                    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                    description varchar(255),
                                    name varchar(255),
                                    is_deleted BOOLEAN DEFAULT FALSE,
                                    CONSTRAINT test_case_priority_pkey PRIMARY KEY (id)
);

CREATE TABLE test_execution_status (
                                    id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                    description varchar(255),
                                    name varchar(255),
                                    is_deleted BOOLEAN DEFAULT FALSE,
                                    CONSTRAINT test_execution_status_pkey PRIMARY KEY (id)
);

CREATE TABLE test_plan_case (
                                id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                                plan_id int8,
                                case_id int8,
                                CONSTRAINT test_test_plan_case_pkey PRIMARY KEY (id)
);

ALTER TABLE test_plan_case ADD CONSTRAINT fk_test_plan_case_plan_id FOREIGN KEY (plan_id) REFERENCES test_plan(id);
ALTER TABLE test_plan_case ADD CONSTRAINT fk_test_plan_case_case_id FOREIGN KEY (case_id) REFERENCES test_case(id);


ALTER TABLE test_case ADD CONSTRAINT fk_test_case_status_id FOREIGN KEY (status_id) REFERENCES test_case_status(id);
ALTER TABLE test_case ADD CONSTRAINT fk_test_case_category_id FOREIGN KEY (category_id) REFERENCES test_case_category(id);
ALTER TABLE test_case ADD CONSTRAINT fk_test_case_priority_id FOREIGN KEY (priority_id) REFERENCES test_case_priority(id);

ALTER TABLE test_run ADD CONSTRAINT fk_test_run_plan_id FOREIGN KEY (plan_id) REFERENCES test_plan(id);

ALTER TABLE test_plan ADD CONSTRAINT fk_test_plan_type_id FOREIGN KEY (plan_type_id) REFERENCES test_plan_type(id);
ALTER TABLE test_plan ADD CONSTRAINT fk_test_plan_parent_id FOREIGN KEY (parent_id) REFERENCES test_plan(id) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE test_execution ADD CONSTRAINT fk_test_execution_case_id FOREIGN KEY (case_id) REFERENCES test_case(id);
ALTER TABLE test_execution ADD CONSTRAINT fk_test_execution_status_id FOREIGN KEY (status_id) REFERENCES test_execution_status(id);
ALTER TABLE test_execution ADD CONSTRAINT fk_test_execution_run_id FOREIGN KEY (run_id) REFERENCES test_run(id);

ALTER TABLE test_execution ADD CONSTRAINT uq_case_run UNIQUE(case_id, run_id);
ALTER TABLE test_plan ADD CONSTRAINT uq_test_plan_name UNIQUE(name);
ALTER TABLE test_case ADD CONSTRAINT uq_test_case_name UNIQUE(name);
ALTER TABLE test_run ADD CONSTRAINT uq_test_run_name UNIQUE(name);

ALTER TABLE test_case_category ADD CONSTRAINT uq_test_case_category_name UNIQUE(name);
ALTER TABLE test_case_priority ADD CONSTRAINT uq_test_case_priority_name UNIQUE(name);
ALTER TABLE test_case_status ADD CONSTRAINT uq_test_case_status_name UNIQUE(name);
ALTER TABLE test_execution_status ADD CONSTRAINT uq_test_execution_status_name UNIQUE(name);
ALTER TABLE test_plan_type ADD CONSTRAINT uq_test_plan_type_name UNIQUE(name);

INSERT INTO public.test_case_category (description, "name") VALUES('Базовая категория', 'default');
INSERT INTO public.test_case_priority (description, "name") VALUES('Низкий приоритет', 'minor');
INSERT INTO public.test_case_priority (description, "name") VALUES('Высокий приоритет', 'major');
INSERT INTO public.test_case_status (description, "name") VALUES('Проверка пройдена', 'ready');
INSERT INTO public.test_case_status (description, "name") VALUES('Требуется проверка', 'in review');

INSERT INTO public.test_execution_status (description, "name") VALUES('Успешно пройден', 'passed');
INSERT INTO public.test_execution_status (description, "name") VALUES('Не пройден', 'failed');
INSERT INTO public.test_execution_status (description, "name") VALUES('Заблокирован', 'blocked');

INSERT INTO public.test_plan_type (description, "name") VALUES('Базовый', 'base');
INSERT INTO public.test_plan_type (description, "name") VALUES('Регрессионное тестирование', 'regress');
INSERT INTO public.test_plan_type (description, "name") VALUES('Минимальное тестирование', 'smoke');

INSERT INTO public.test_plan (parent_id, author_id, plan_type_id, creation_date, is_active, description, "name")
SELECT null, null, id, CURRENT_TIMESTAMP, true, 'Base test plan', 'base' from test_plan_type where name='base';

--rollback truncate table testcase;