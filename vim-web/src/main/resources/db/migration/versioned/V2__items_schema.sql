create table inventory_items
(
    id     serial                not null
        constraint inventory_items_pk
            primary key,
    active boolean default false not null,
    uuid   varchar(36)
);

create unique index inventory_items_id_uindex
    on inventory_items (id);

create unique index inventory_items_uuid_uindex
    on inventory_items (uuid);