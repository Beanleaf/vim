create table users
(
    id            serial                not null
        constraint employees_pk
            primary key,
    username      varchar(10)           not null,
    active        boolean default false not null,
    password_salt bytea,
    password_hash bytea,
    email_address varchar(256),
    phonenumber   varchar(25),
    first_name    varchar(255),
    last_name     varchar(255),
    uuid          varchar(36),
    user_role     int
);

create unique index users_id_uindex
    on users (id);

create unique index users_username_uindex
    on users (username);

create unique index users_emailaddress_uindex
    on users (email_address);

create unique index users_uuid_uindex
    on users (uuid);