insert into test_schema.users (username, password, email)
values ('bob', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('john', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into test_schema.roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into test_schema.users_roles (user_id, role_id)
values (1, 1),
       (2, 2);


INSERT INTO test_schema.clients (id,username)
VALUES (1, 'bob'),
       (2,'john');
