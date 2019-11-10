create table users
(
    id           serial                not null
        constraint employees_pk
            primary key,
    username     varchar(10)           not null,
    active       boolean default false not null,
    passwordsalt bytea,
    passwordhash bytea,
    emailaddress varchar(256),
    firstname    varchar(255),
    lastname     varchar(255),
    uuid         varchar(36)
);

create unique index users_id_uindex
    on users (id);

create unique index users_username_uindex
    on users (username);

create unique index users_emailaddress_uindex
    on users (emailaddress);

create unique index users_uuid_uindex
    on users (uuid);