INSERT INTO pabw_core.products(id, name, product_type_id, product_status_id, int_rate, dt_created, dt_closed, dt_last_update)
VALUES (-1,'Technical',1,1,15,'2009-10-23 13:49:25.000000',null,'2009-10-23 13:49:25.000000'),
       (1,'Credit Product',0,0,15,'2009-10-23 13:49:25.000000',null,'2009-10-23 13:49:25.000000'),
       (2,'Deposit Product',1,0,25,'2009-10-23 13:49:25.000000',null,'2009-10-23 13:49:25.000000');

INSERT INTO pabw_core.accounts(username, account_type_id, account_number, product_id, account_status_id, currency_id, dt_created, dt_blocked, dt_closed, dt_last_update)
VALUES ('John',1,'D000000001',2,0,0,'2009-10-23 13:49:25.000000',null,null,'2009-10-23 13:49:25.000000'),
       ('John',0,'C000000001',1,0,0,'2009-10-23 13:49:26.000000',null,null,'2009-10-23 13:49:25.000000'),
       ('Jack',0,'C000000002',1,0,0,'2009-10-23 13:49:26.000000',null,null,'2009-10-23 13:49:25.000000'),
       ('Technical',2,'T1001',-1,0,0,'2009-10-23 13:49:26.000000',null,null,'2009-10-23 13:49:25.000000');


INSERT INTO pabw_core.balances(account_id, credit_balance, debit_balance, credit_debt, dt_last_update)
VALUES (1,500,500,500,'2009-10-23 13:49:25.000000'),
       (2,500,500,500,'2009-10-23 13:49:25.000000'),
       (3,15000,0,30000,'2009-10-23 13:49:25.000000'),
       (4,0,1000000,0,'2009-10-23 13:49:25.000000');
