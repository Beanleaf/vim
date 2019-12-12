alter table users
    alter column email_address drop not null;

drop index users_email_address_uindex;