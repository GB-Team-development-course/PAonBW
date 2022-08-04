create table pabw_auth.users_roles
(
    user_id    bigint not null references pabw_auth.users (id),
    role_id    bigint not null references pabw_auth.roles (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (user_id, role_id)
);
