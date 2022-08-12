INSERT INTO pabw_core.products(id, name, product_type_id, product_status_id, int_rate, dt_created, dt_closed, dt_last_update)
VALUES (1,'prod',1,1,15,'2009-10-23 13:49:25.000000',null,'2009-10-23 13:49:25.000000');

INSERT INTO pabw_core.accounts (id,username, account_type_id, account_number, product_id, account_status_id,
                                  currency_id, dt_created, dt_blocked, dt_closed)
VALUES (1,'John', 0, 'C000000001', 1, 0, 0, '2009-10-23 13:49:25.000000', null, null),
       (2,'Jack', 0, 'C000000002', 1, 0, 0, '2009-10-23 13:49:26.000000', null, null),
       (3,'Bob', 0, 'C000000003', 1, 0, 0, '2009-10-23 13:49:27.000000', null, null),
       (4,'Bill', 0, 'C000000004', 1, 0, 0, '2009-10-23 13:49:28.000000', null, null),
       (5,'John', 1, 'D000000001', 1, 0, 0, '2009-10-23 13:49:29.000000', null, null),
       (6,'Jack', 1, 'D000000002', 1, 0, 0, '2009-10-23 13:49:30.000000', null, null),
       (7,'Bob', 1, 'D000000003', 1, 0, 0, '2009-10-23 13:49:31.000000', null, null),
       (8,'Tech', 1, 'T1001', 1, 0, 0, '2009-10-23 13:49:31.000000', null, null);

INSERT INTO pabw_core.balances (id,account_id, credit_balance, debit_balance, credit_debt)
VALUES (1,1, 500, 0, 200),
       (2,2, 800, 0, 500),
       (3,3, 1000, 0, 700),
       (4,4, 2000, 0, 900),
       (5,5, 0, 700, 0),
       (6,6, 0, 389, 0),
       (7,7, 0, 0, 0),
       (8,8, 10000, 10000, 10000);
