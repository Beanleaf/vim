-- auto-generated definition
create table users
(
    id       serial      not null
        constraint employees_pk
            primary key,
    username varchar(10) not null
);

create unique index users_id_uindex
    on users (id);

create unique index users_username_uindex
    on users (username);

