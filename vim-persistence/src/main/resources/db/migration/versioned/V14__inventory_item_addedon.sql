alter table inventory_items
    add added_on timestamptz;

update inventory_items
set added_on = now();

alter table inventory_items
    alter column added_on set not null;
