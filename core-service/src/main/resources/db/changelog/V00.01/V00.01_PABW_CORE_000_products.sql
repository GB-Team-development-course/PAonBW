CREATE TABLE pabw_core.products
(
    id BIGSERIAL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL ,
    product_type_id INTEGER NOT NULL ,
    product_status_id INTEGER NOT NULL ,
    int_rate NUMERIC NOT NULL ,
    dt_created TIMESTAMP NOT NULL ,
    dt_closed TIMESTAMP,
    dt_last_update TIMESTAMP DEFAULT current_timestamp
);
