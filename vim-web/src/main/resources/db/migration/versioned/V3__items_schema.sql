create table inventory_items
(
    id               serial                not null
        constraint inventory_items_pk
            primary key,
    active           boolean default false not null,
    uuid             varchar(36),
    description      varchar(255),
    item_category_id int
);

create unique index inventory_items_id_uindex
    on inventory_items (id);

create unique index inventory_items_uuid_uindex
    on inventory_items (uuid);

alter table inventory_items
    add constraint inventory_items_item_categories_id_fk
        foreign key (item_category_id) references item_categories;