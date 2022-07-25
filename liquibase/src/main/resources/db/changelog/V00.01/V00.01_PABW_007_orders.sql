CREATE TABLE test_schema.orders
(
    id                     BIGSERIAL PRIMARY KEY,
    external_order_guid    UUID   NOT NULL,
    transfer_direction_id  INTEGER   NOT NULL,
    source_account         VARCHAR   NOT NULL,
    target_account         VARCHAR   NOT NULL,
    amount                 NUMERIC   NOT NULL,
    currency_id            INTEGER   NOT NULL,
    payment_purpose        VARCHAR   NOT NULL,
    additional_information VARCHAR,
    order_status_id        INTEGER   NOT NULL,
    execution_start        TIMESTAMP NOT NULL,
    execution_end          TIMESTAMP
);