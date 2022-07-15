create table test_schema.users
(
    id         bigserial primary key,
    username   varchar(36) not null unique,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table test_schema.roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table test_schema.users_roles
(
    user_id    bigint not null references test_schema.users (id),
    role_id    bigint not null references test_schema.roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);
