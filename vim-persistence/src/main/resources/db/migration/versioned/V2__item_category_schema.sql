create table item_categories
(
    id          serial      not null
        constraint item_categories_pk
            primary key,
    short_code  varchar(10) not null,
    description varchar(255)
);

create unique index item_categories_id_uindex
    on item_categories (id);

create unique index item_categories_short_code_uindex
    on item_categories (short_code);