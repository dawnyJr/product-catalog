# --- !Ups

set ignorecase true;

create table products(
id long,
ean long,
name varchar,
description varchar);

create table warehouses(
id long,
name varchar);

create table stock_items(
id long,
product_id long,
warehouse_id long,
quantity long);

# --- !Downs

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS warehouses;
DROP TABLE IF EXISTS stock_items;