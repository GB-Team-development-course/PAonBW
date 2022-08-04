CREATE TABLE pabw_credit.interests
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(255) NOT NULL ,
    target_account VARCHAR(255) NOT NULL ,
    amount NUMERIC NOT NULL ,
    currency_id INTEGER NOT NULL ,
    interest_status_id INTEGER NOT NULL ,
    execution_start TIMESTAMP NOT NULL ,
    execution_end TIMESTAMP
);
