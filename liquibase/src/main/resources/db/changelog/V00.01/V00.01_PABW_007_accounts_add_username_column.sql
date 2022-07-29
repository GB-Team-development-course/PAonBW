DROP INDEX IF EXISTS test_schema.accounts_client_id_fkey;

ALTER TABLE test_schema.accounts
    ADD account_username varchar(36) NOT NULL,
    DROP COLUMN client_id;

CREATE INDEX ix_account_username
    ON test_schema.accounts (account_username);
