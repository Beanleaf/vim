alter table inventory_items
    add is_deleted boolean default false not null;