CREATE SCHEMA pabw_core;
CREATE USER pabw_core_user WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON SCHEMA pabw_core TO pabw_core_user;

CREATE SCHEMA pabw_auth;
CREATE USER pabw_auth_user WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON SCHEMA pabw_auth TO pabw_auth_user;

CREATE SCHEMA pabw_debit;
CREATE USER pabw_debit_user WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON SCHEMA pabw_debit TO pabw_debit_user;

CREATE SCHEMA pabw_credit;
CREATE USER pabw_credit_user WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON SCHEMA pabw_credit TO pabw_credit_user;