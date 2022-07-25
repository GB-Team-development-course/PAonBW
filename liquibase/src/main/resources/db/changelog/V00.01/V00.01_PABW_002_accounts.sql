CREATE TABLE test_schema.accounts
(
    id BIGSERIAL PRIMARY KEY ,
    client_id BIGINT NOT NULL REFERENCES test_schema.clients(id),
    account_type_id INTEGER NOT NULL ,
    account_number VARCHAR(255) NOT NULL ,
    product_id BIGINT NOT NULL REFERENCES test_schema.products(id),
    account_status_id INTEGER NOT NULL ,
    currency_id INTEGER NOT NULL ,
    dt_created TIMESTAMP NOT NULL ,
    dt_blocked TIMESTAMP,
    dt_closed TIMESTAMP,
    dt_last_update TIMESTAMP DEFAULT current_timestamp
);