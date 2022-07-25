CREATE TABLE test_schema.accruals
(
    id BIGSERIAL PRIMARY KEY ,
    target_account VARCHAR(255) NOT NULL ,
    amount NUMERIC NOT NULL ,
    currency_id INTEGER NOT NULL ,
    accrual_status_id INTEGER NOT NULL ,
    execution_start TIMESTAMP NOT NULL ,
    execution_end TIMESTAMP
);