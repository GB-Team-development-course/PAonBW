INSERT INTO pabw_core.products(id, name, product_type_id, product_status_id, int_rate, dt_created, dt_closed, dt_last_update)
VALUES (1,'prod',1,1,15,'2009-10-23 13:49:25.000000',null,'2009-10-23 13:49:25.000000');

INSERT INTO pabw_core.accounts(id, username, account_type_id, account_number, product_id, account_status_id, currency_id, dt_created, dt_blocked, dt_closed, dt_last_update)
VALUES (1,'John',1,'D000000001',1,0,0,'2009-10-23 13:49:25.000000',null,null,'2009-10-23 13:49:25.000000'),
       (2,'John',0,'C000000001',1,0,0,'2009-10-23 13:49:26.000000',null,null,'2009-10-23 13:49:25.000000');


INSERT INTO pabw_core.balances(id, account_id, credit_balance, debit_balance, credit_debt, dt_last_update)
VALUES (1,1,500,500,500,'2009-10-23 13:49:25.000000'),
       (2,2,500,500,500,'2009-10-23 13:49:25.000000');