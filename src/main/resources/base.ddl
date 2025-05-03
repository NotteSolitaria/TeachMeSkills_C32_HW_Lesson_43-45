create table products
(
    id           integer                 not null
        constraint products_pk
            primary key,
    product_name varchar                 not null,
    created      timestamp default now() not null,
    updated      timestamp default now() not null
);

alter table products
    owner to postgres;

-- auto-generated definition
create table security
(
    id       integer                                     not null
        constraint security_pk
            primary key,
    login    varchar                                     not null,
    password varchar                                     not null,
    user_id  integer
        constraint security_users_id_fk
            references users,
    role     varchar   default 'USER'::character varying not null,
    created  timestamp default now(),
    updated  timestamp default now()
);

alter table security
    owner to postgres;

-- auto-generated definition
create table users
(
    id          integer                 not null
        constraint users_pk
            primary key,
    first_name  varchar(10)             not null,
    second_name varchar(10)             not null,
    age         integer                 not null,
    email       varchar                 not null,
    is_deleted  boolean   default false not null,
    created     timestamp default now(),
    updated     timestamp default now()
);

alter table users
    owner to postgres;

