ALTER TABLE test_schema.clients
    ADD userName varchar(36) NOT NULL;

CREATE UNIQUE INDEX ux_clients_username
    ON test_schema.clients (userName);
