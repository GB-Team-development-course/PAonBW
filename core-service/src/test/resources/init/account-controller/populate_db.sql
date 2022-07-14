INSERT INTO test_schema.clients (id, username)
VALUES (1, '1'),
       (2, '2'),
       (3, '3'),
       (4, '4'),
       (5, '5');

INSERT INTO test_schema.accounts (client_id, account_type_id, account_number, account_status_id,
                      currency_id, dt_created, dt_blocked, dt_closed)
VALUES (1, 1, 'C000000001', 1, 1, '2009-10-23 13:49:25.000000', null, null),
       (2, 1, 'C000000002', 1, 1, '2009-10-23 13:49:26.000000', null, null),
       (3, 1, 'C000000003', 1, 1, '2009-10-23 13:49:27.000000', null, null),
       (4, 1, 'C000000004', 1, 1, '2009-10-23 13:49:28.000000', null, null),
       (1, 2, 'D000000001', 1, 1, '2009-10-23 13:49:29.000000', null, null),
       (2, 2, 'D000000002', 1, 1, '2009-10-23 13:49:30.000000', null, null),
       (3, 2, 'D000000003', 1, 1, '2009-10-23 13:49:31.000000', null, null);

INSERT INTO test_schema.balances (account_id, credit_balance, debit_balance, credit_debt)
VALUES (1, 500, 0, 200),
       (2, 800, 0, 500),
       (3, 1000, 0, 700),
       (4, 2000, 0, 900),
       (5, 0, 700, 0),
       (6, 0, 389, 0),
       (7, 0, 0, 0);
