alter table events
    add venue_id int not null;

alter table events
    add deleted bool;

alter table events
    add constraint events_sales_outlets_id_fk
        foreign key (venue_id) references sales_outlets;