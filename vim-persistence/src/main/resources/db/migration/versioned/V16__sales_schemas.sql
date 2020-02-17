create table sales_outlets
(
    id          serial       not null
        constraint sales_outlets_pk
            primary key,
    name        varchar(255) not null,
    description varchar(255)
);

create unique index sales_outlets_id_uindex
    on sales_outlets (id);

create table sales_transactions
(
    id              serial      not null
        constraint sales_transactions_pk
            primary key,
    outlet_id       int         not null
        constraint sales_transactions_sales_outlets_id_fk references sales_outlets,
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
    active      bool
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
        constraint products_sales_outlets_is_fk
            references sales_outlets,
    active               bool
);
create unique index products_id_uindex
    on products (id);

create table products_in_transaction
(
    id                   serial not null
        constraint products_in_transaction_pk
            primary key,
    product_id           int    not null
        constraint products_in_transaction_products_id_fk references products,
    sales_transaction_id int    not null
        constraint products_in_transaction_sales_transactions_id_fk references sales_transactions,
    amount               int    not null
);
create unique index products_in_transaction_id_uindex on products_in_transaction (id);

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

create table product_counts
(
    id          serial      not null
        constraint product_counts_pk primary key,
    timestamp   timestamptz not null,
    user_id     int         not null
        constraint product_counts_users_id_fk references users,
    crate_count int,
    loose_count int,
    confirmed   bool,
    event_id    int
        constraint product_counts_events_id_fk references events
);
create unique index product_counts_id_uindex on product_counts (id);