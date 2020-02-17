create table events
(
    id                 serial       not null
        constraint events_pk
            primary key,
    name               varchar(256) not null,
    start_time         timestamp with time zone,
    end_time           timestamp with time zone,
    planned_by_user_id integer      not null
        constraint events_users_id_fk
            references users
);

create unique index events_id_uindex
    on events (id);

create index events_planned_by_user_id_index
    on events (planned_by_user_id);
