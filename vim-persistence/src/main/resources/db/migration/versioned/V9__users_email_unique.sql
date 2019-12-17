alter table users
    alter column email_address set not null;

create unique index users_email_address_uindex
    on users (email_address);