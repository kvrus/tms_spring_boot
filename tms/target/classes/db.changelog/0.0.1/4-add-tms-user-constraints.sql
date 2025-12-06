--liquibase formatted sql

--changeset Koss:4
--comment add tms user constraints

ALTER TABLE test_case ADD CONSTRAINT fk_test_case_author_id FOREIGN KEY (author_id) REFERENCES users(id);
ALTER TABLE test_case ADD CONSTRAINT fk_test_case_assignee_tester_id FOREIGN KEY (assignee_tester_id) REFERENCES users(id)  DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE test_case ADD CONSTRAINT fk_test_case_reviewer_tester_id FOREIGN KEY (reviewer_tester_id) REFERENCES users(id)  DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE test_run ADD CONSTRAINT fk_test_run_author_id FOREIGN KEY (author_id) REFERENCES users(id);

ALTER TABLE test_plan ADD CONSTRAINT fk_test_plan_author_id FOREIGN KEY (author_id) REFERENCES users(id);

ALTER TABLE test_execution ADD CONSTRAINT fk_test_execution_tester_id FOREIGN KEY (tester_id) REFERENCES users(id);