select * from users;
select * from user_role;
select * from test_plan;
select * from test_plan_history;
select * from test_case;
select count(*) from test_plan_case;

-- Setup userss

DO $$
DECLARE
i INT;
BEGIN
FOR i IN 1..1000 LOOP
		INSERT INTO public.users
		(username, "password")
		VALUES('User  ' || i, '$2a$10$gpcIA.1aQSzqOnPNO0.VwemYLq/P197XRxzHVg9EHTViEhyrtvbI2');
END LOOP;
END $$;


-- Setup userss

DO $$
DECLARE
i INT;
BEGIN
FOR i IN 1..1000 LOOP
		INSERT INTO public.users
		(username, "password")
		VALUES('User  ' || i, '$2a$10$gpcIA.1aQSzqOnPNO0.VwemYLq/P197XRxzHVg9EHTViEhyrtvbI2');
END LOOP;
END $$;

-- Setup user roles

DO $$
DECLARE
user_record RECORD;
BEGIN
FOR user_record IN SELECT * FROM users LOOP
    INSERT INTO public.user_role
                   (user_id, role_id)
                   VALUES(user_record.id, 1);
END LOOP;
END $$;

-- Setup test plans

DO $$
DECLARE
i INT;
BEGIN
FOR i IN 1..1000 LOOP
		INSERT INTO public.test_plan
			(parent_id, author_id, plan_type_id, creation_date, is_active, description, "name", is_deleted)
			VALUES(null, 1, 1, NOW(), true, ' description  ' || i, 'title' || i, false);
END LOOP;
END $$;

-- Setup test cases for plans

DO $$
DECLARE
i INT;
BEGIN
FOR i IN 1..1000 LOOP
		INSERT INTO public.test_case
		(author_id, status_id, category_id, assignee_tester_id, priority_id, reviewer_tester_id, creation_date, is_automated, is_deleted, description, requirements, "name")
		VALUES(1, 1, 1, 1, 1, 1, NOW(), false, false, 'description' || i, 'requirements' || i, 'name'||i);
END LOOP;
END $$;

-- Link test cases for plans

DO $$
DECLARE
test_plan_record RECORD;
	test_case_record RECORD;
BEGIN
FOR test_plan_record IN SELECT * FROM public.test_plan LOOP
    FOR test_case_record IN SELECT * FROM public.test_case LOOP
                        INSERT INTO public.test_plan_case
                        (plan_id, case_id)
                        VALUES(test_plan_record.id, test_case_record.id);
END LOOP;
END LOOP;
END $$;

-- ADD HISTORY OF CHANGES FOR TEST PLANS

CREATE TABLE test_plan_history (
                                   log_id SERIAL,
                                   plan_id INT,
                                   plan_name VARCHAR(256),
                                   plan_description text,
                                   plan_is_deleted bool,
                                   plan_is_active bool,
                                   updated_at TIMESTAMP not null,
                                   PRIMARY KEY (log_id, updated_at)
) partition by range(updated_at);

-- Function to create next partition, should be called before next week started

CREATE OR REPLACE FUNCTION create_week_partition()
RETURNS VOID AS $$
DECLARE
current_week_start DATE := date_trunc('week', CURRENT_DATE);
    current_week_end DATE := current_week_start + INTERVAL '1 week';
    current_partition_name TEXT := 'test_plan_history_week_' || to_char(current_week_start, 'IYYY_IW');
	next_week_start DATE := date_trunc('week', CURRENT_DATE + INTERVAL '1 week');
    next_week_end DATE := next_week_start + INTERVAL '1 week';
    next_partition_name TEXT := 'test_plan_history_week_' || to_char(next_week_start, 'IYYY_IW');
BEGIN
	-- current week
EXECUTE format('
        CREATE TABLE IF NOT EXISTS %I PARTITION OF test_plan_history
        FOR VALUES FROM (%L) TO (%L)',
               current_partition_name,
               current_week_start,
               current_week_end
        );
-- next week
EXECUTE format('
        CREATE TABLE IF NOT EXISTS %I PARTITION OF test_plan_history
        FOR VALUES FROM (%L) TO (%L)',
               next_partition_name,
               next_week_start,
               next_week_end
        );
END;
$$ LANGUAGE plpgsql;

SELECT create_week_partition();


-- TO CHeck partitions created

SELECT
    child.relname AS partition_name,
    parent.relname AS parent_table
FROM
    pg_inherits AS i
        JOIN
    pg_class AS child ON child.oid = i.inhrelid
        JOIN
    pg_class AS parent ON parent.oid = i.inhparent
WHERE
    parent.relname = 'test_plan_history';

-- FETCH CERTAIN PARTITION

select * from test_plan_history_week_2024_44;

CREATE OR REPLACE PROCEDURE save_history_plan(
	new_plan_id INT,
    new_plan_name VARCHAR,
    new_plan_description text,
    new_plan_is_deleted bool,
    new_plan_is_active bool
)
LANGUAGE plpgsql AS
$$
BEGIN
INSERT INTO test_plan_history (plan_id, plan_name, plan_description, plan_is_deleted, plan_is_active, updated_at)
VALUES (new_plan_id, new_plan_name, new_plan_description, new_plan_is_deleted, new_plan_is_active, NOW());
END;
$$;


CREATE OR REPLACE FUNCTION on_plan_update()
RETURNS TRIGGER AS
$$
BEGIN
CALL save_history_plan(CAST(NEW.id AS INT) , NEW.name, NEW.description, NEW.is_deleted, NEW.is_active);
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


drop trigger if exists plan_update_trigger on public.test_plan;

CREATE TRIGGER plan_update_trigger
    AFTER insert or UPDATE ON public.test_plan
                        FOR EACH ROW
                        EXECUTE FUNCTION on_plan_update();


UPDATE public.test_plan
SET description='New description here123', "name"='new name here123'
WHERE id=7;

INSERT INTO public.test_plan
(parent_id, author_id, plan_type_id, creation_date, is_active, description, "name", is_deleted)
VALUES(null, 1, 1, NOW(), true, 'new descrioption week 45 1 second ', 'new plan week 45 1 second', false);

