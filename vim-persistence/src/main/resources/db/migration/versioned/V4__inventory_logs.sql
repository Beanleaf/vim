create table inventory_logs
(
    id                  serial      not null
        constraint inventory_logs_pk
            primary key,
    inventory_item_id   int         not null,
    user_id             int         not null,
    timestamp           timestamptz not null,
    inventory_direction int         not null,
    comment             varchar(255)
);

create unique index inventory_logs_id_uindex
    on inventory_logs (id);

alter table inventory_logs
    add constraint inventory_logs_inventory_items_id_fk
        foreign key (inventory_item_id) references inventory_items;

alter table inventory_logs
    add constraint inventory_logs_users_id_fk
        foreign key (user_id) references users;