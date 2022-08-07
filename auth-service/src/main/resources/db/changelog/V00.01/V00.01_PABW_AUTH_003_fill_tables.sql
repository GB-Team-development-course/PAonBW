insert into pabw_auth.roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');


insert into pabw_auth.users (username, password, email)
values ('Jack', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('John', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into pabw_auth.users_roles (user_id, role_id)
values (1, 1),
       (2, 1);
