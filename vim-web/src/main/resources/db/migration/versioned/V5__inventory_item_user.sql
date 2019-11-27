alter table inventory_items
    add added_by_user_id int not null;

alter table inventory_items
    add constraint inventory_items_users_id_fk
        foreign key (added_by_user_id) references users;