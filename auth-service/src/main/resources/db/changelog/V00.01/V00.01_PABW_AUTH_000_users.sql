create table pabw_auth.users
(
    id         bigserial primary key,
    username   varchar(36) not null unique,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);