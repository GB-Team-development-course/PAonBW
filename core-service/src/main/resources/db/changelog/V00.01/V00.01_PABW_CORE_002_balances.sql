CREATE TABLE pabw_core.balances
(
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES pabw_core.accounts(id),
    credit_balance NUMERIC,
    debit_balance NUMERIC,
    credit_debt NUMERIC,
    dt_last_update TIMESTAMP DEFAULT current_timestamp
);