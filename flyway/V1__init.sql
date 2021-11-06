create table categories
(
    id         bigserial primary key,
    title      varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
insert into categories (title)
values ('Food');

create table products
(
    id          bigserial primary key,
    title       varchar(255),
    price       numeric (8, 2) not null,
    category_id bigint references categories (id),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);
insert into products (title, price, category_id)
values ('Bread', 25, 1),
       ('Milk', 80, 1),
       ('Cheese', 450, 1),
       ('A 4', 450, 1),
       ('A 5', 450, 1),
       ('A 6', 450, 1),
       ('A 7', 450, 1),
       ('A 8', 450, 1),
       ('A 9', 450, 1),
       ('A 10', 450, 1),
       ('A 11', 450, 1),
       ('A 12', 450, 1),
       ('A 13', 450, 1),
       ('A 14', 450, 1),
       ('A 15', 450, 1),
       ('A 16', 450, 1),
       ('A 17', 450, 1),
       ('A 18', 450, 1),
       ('A 19', 450, 1),
       ('A 20', 450, 1),
       ('A 21', 450, 1),
       ('A 22', 450, 1),
       ('A 23', 450, 1),
       ('A 24', 450, 1),
       ('A 25', 450, 1),
       ('A 26', 450, 1);

create table users
(
    id         bigserial primary key,
    username   varchar(30) not null unique,
    first_name   varchar(80) not null,
    last_name   varchar(80) not null,
    password   varchar(80) not null,
    email      varchar(50) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table roles
(
    id         bigserial primary key,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

insert into roles (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

insert into users (username, first_name, last_name, password, email)
values ('user', 'Bob', 'Johnson', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'bob_johnson@gmail.com'),
       ('admin', 'John', 'Johnson', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'john_johnson@gmail.com');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

create table orders
(
    id         bigserial primary key,
    username   varchar(255) references users (username),
    address    varchar(255),
    phone      varchar(255),
    price      numeric (8, 2) not null,
    status      varchar(64),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into orders (username, address, phone, price, status)
values ('user', '1111', '1111', 100, 'Не оплачен');

create table order_items
(
    id                bigserial primary key,
    order_id          bigint references orders (id),
    product_id        bigint references products (id),
    quantity          integer,
    price_per_product numeric (8, 2) not null,
    price             numeric (8, 2) not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

