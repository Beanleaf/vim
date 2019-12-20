alter table users
    rename column first_name to name;
alter table users
    drop column last_name;