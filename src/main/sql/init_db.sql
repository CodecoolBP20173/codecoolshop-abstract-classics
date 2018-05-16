ALTER TABLE IF EXISTS product DROP CONSTRAINT IF EXISTS fk_supplier_id CASCADE;
ALTER TABLE IF EXISTS product DROP CONSTRAINT IF EXISTS fk_product_category_id CASCADE;
ALTER TABLE IF EXISTS order_products DROP CONSTRAINT IF EXISTS fk_order_id CASCADE;
ALTER TABLE IF EXISTS order_products DROP CONSTRAINT IF EXISTS fk_product_id CASCADE;


DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_products;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS product_id_seq;
DROP SEQUENCE IF EXISTS supplier_id_seq;
DROP SEQUENCE IF EXISTS product_category_id_seq;
DROP SEQUENCE IF EXISTS orders_id_seq;
DROP SEQUENCE IF EXISTS users_id_seq;


CREATE SEQUENCE product_id_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  NO MAXVALUE;

CREATE TABLE product (
    id INTEGER NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    default_price FLOAT NOT NULL,
    default_currency VARCHAR(3),
    product_category_id INTEGER NOT NULL,
    supplier_id  INTEGER NOT NULL,
    product_image VARCHAR(255) NOT NULL
);



ALTER TABLE product ADD CONSTRAINT pk_product_id PRIMARY KEY (id);

CREATE SEQUENCE supplier_id_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  NO MAXVALUE;

CREATE TABLE supplier (
    id INTEGER NOT NULL DEFAULT nextval('supplier_id_seq'::regclass),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL

);


ALTER TABLE supplier ADD CONSTRAINT pk_supplier_id PRIMARY KEY (id);

CREATE SEQUENCE product_category_id_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  NO MAXVALUE;

CREATE TABLE product_category (
    id INTEGER NOT NULL DEFAULT nextval('product_category_id_seq'::regclass),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);


ALTER TABLE product_category ADD CONSTRAINT pk_product_category_id PRIMARY KEY (id);

CREATE SEQUENCE orders_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE;

CREATE TABLE orders (
    id INTEGER NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    name VARCHAR(255),
    description VARCHAR(255),
    email VARCHAR(255),
    phone CHAR(15),
    billing_address VARCHAR(255),
    shipping_address VARCHAR(255),
    payment_method VARCHAR(255)
);


ALTER TABLE orders ADD CONSTRAINT pk_orders_id PRIMARY KEY (id);


CREATE TABLE order_products (
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL
);


DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_id_seq;
ALTER TABLE IF EXISTS users DROP CONSTRAINT IF EXISTS pk_users_id CASCADE;

CREATE SEQUENCE users_id_seq
  START WITH 0
  INCREMENT BY 1
  MINVALUE 0
  NO MAXVALUE;

CREATE TABLE users (
  id INTEGER NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone_number NUMERIC NOT NULL,
  billing_address VARCHAR(255) NOT NULL,
  shipping_address VARCHAR(255) NOT NULL
);

ALTER SEQUENCE users_id_seq RESTART WITH 0;

ALTER TABLE users ADD CONSTRAINT pk_users_id PRIMARY KEY (id);



ALTER TABLE product ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);
ALTER TABLE product ADD CONSTRAINT fk_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);

ALTER TABLE order_products ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE order_products ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product(id);