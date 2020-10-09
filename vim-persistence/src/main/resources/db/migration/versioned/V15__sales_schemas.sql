create table venues
(
    id          serial       not null
        constraint venues_pk
            primary key,
    name        varchar(255) not null,
    description varchar(255),
    deleted     bool         not null
);

create unique index venues_id_uindex
    on venues (id);

create table sales_transactions
(
    id              serial      not null
        constraint sales_transactions_pk
            primary key,
    outlet_id       int         not null
        constraint sales_transactions_venues_id_fk references venues,
    timestamp       timestamptz not null,
    wholesale_price float8      not null
);

create unique index sales_transactions_id_uindex
    on sales_transactions (id);

create table product_categories
(
    id          serial       not null
        constraint product_categories_pk primary key,
    short_code  varchar(10)  not null,
    description varchar(255) not null,
    active bool not null
);
create unique index product_categories_id_uindex on product_categories (id);
create unique index product_categories_short_code_uindex on product_categories (short_code);

create table products
(
    id                   serial       not null
        constraint products_pk
            primary key,
    description          varchar(255) not null,
    wholesale_price      float8       not null,
    name                 varchar(255) not null,
    supplier             varchar(255),
    in_crates            bool,
    product_categorie_id int          not null
        constraint products_product_categories_id_fk
            references product_categories,
    sales_outlet_id      int          not null
        constraint products_venues_is_fk
            references venues,
    deleted              bool         not null
);
create unique index products_id_uindex
    on products (id);

create table payments
(
    id                   serial not null
        constraint payments_pk primary key,
    sales_transaction_id int    not null
        constraint payments_sales_transactions_id_fk references sales_transactions,
    amount               float8 not null,
    payment_type         int    not null
);
create unique index payments_id_uindex on payments (id);

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
            references users,
    venue_id           integer      not null
        constraint events_venues_id_fk
            references venues,
    deleted            bool         not null
);

create unique index events_id_uindex
    on events (id);

create index events_planned_by_user_id_index
    on events (planned_by_user_id);

create table stock_counts
(
    id          serial      not null
        constraint stock_counts_pk primary key,
    timestamp   timestamptz not null,
    user_id     int         not null
        constraint stock_counts_users_id_fk references users,
    crate_count int,
    loose_count int,
    confirmed   bool,
    event_id    int
        constraint stock_counts_events_id_fk references events
);
create unique index stock_counts_id_uindex on stock_counts (id);