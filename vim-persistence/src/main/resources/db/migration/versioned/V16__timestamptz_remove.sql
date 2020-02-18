alter table events
    alter column start_time type timestamp using start_time::timestamp;
alter table events
    alter column end_time type timestamp using end_time::timestamp;
alter table inventory_items
    alter column added_on type timestamp using added_on::timestamp;
alter table inventory_logs
    alter column timestamp type timestamp using timestamp::timestamp;
alter table product_counts
    alter column timestamp type timestamp using timestamp::timestamp;
alter table sales_transactions
    alter column timestamp type timestamp using timestamp::timestamp;