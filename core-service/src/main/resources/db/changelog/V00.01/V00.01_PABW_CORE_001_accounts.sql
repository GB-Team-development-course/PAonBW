CREATE TABLE pabw_core.accounts
(
    id BIGSERIAL PRIMARY KEY ,
    username varchar(255) NOT NULL,
    account_type_id INTEGER NOT NULL ,
    account_number VARCHAR(255) NOT NULL ,
    product_id BIGINT NOT NULL REFERENCES pabw_core.products(id),
    account_status_id INTEGER NOT NULL ,
    currency_id INTEGER NOT NULL ,
    dt_created TIMESTAMP NOT NULL ,
    dt_blocked TIMESTAMP,
    dt_closed TIMESTAMP,
    dt_last_update TIMESTAMP DEFAULT current_timestamp
);