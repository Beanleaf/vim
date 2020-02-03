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
    outlet_id       int         not null,
    timestamp       timestamptz not null,
    wholesale_price float8      not null,
    retail_price    float8      not null
);

create unique index sales_transactions_id_uindex
    on sales_transactions (id);

alter table sales_transactions
    add constraint sales_transactions_sales_outlets_id_fk
        foreign key (outlet_id) references sales_outlets;

create table products
(
    id              serial       not null
        constraint products_pk
            primary key,
    wholesale_price float8       not null,
    retail_price    float8       not null,
    name            varchar(255) not null
);
create unique index products_id_uindex
    on products (id);

create table products_in_transaction
(
    id                   serial not null
        constraint products_in_transaction_pk
            primary key,
    product_id           int    not null,
    sales_transaction_id int    not null,
    amount               int    not null
);
create unique index products_in_transaction_id_uindex on products_in_transaction (id);
alter table products_in_transaction
    add constraint products_in_transaction_products_id_fk
        foreign key (product_id) references products;
alter table products_in_transaction
    add constraint products_in_transaction_sales_transactions_id_fk
        foreign key (sales_transaction_id) references sales_transactions;

create table payments
(
    id                   serial not null
        constraint payments_pk primary key,
    sales_transaction_id int    not null,
    amount               float8 not null,
    payment_type         int    not null
);
create unique index payments_id_uindex on payments (id);
alter table payments
    add constraint payments_sales_transactions_id_fk
        foreign key (sales_transaction_id) references sales_transactions;