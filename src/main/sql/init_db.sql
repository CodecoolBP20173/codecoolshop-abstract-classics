ALTER TABLE IF EXISTS product DROP CONSTRAINT IF EXISTS fk_supplier_id CASCADE;
ALTER TABLE IF EXISTS product DROP CONSTRAINT IF EXISTS fk_product_category_id CASCADE;
ALTER TABLE IF EXISTS order_products DROP CONSTRAINT IF EXISTS fk_order_id CASCADE;
ALTER TABLE IF EXISTS order_products DROP CONSTRAINT IF EXISTS fk_product_id CASCADE;


DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS order_products;

DROP SEQUENCE IF EXISTS product_id_seq;
DROP SEQUENCE IF EXISTS supplier_id_seq;
DROP SEQUENCE IF EXISTS product_category_id_seq;
DROP SEQUENCE IF EXISTS orders_id_seq;


CREATE TABLE product (
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    default_price FLOAT NOT NULL,
    default_currency VARCHAR(3),
    product_category_id INTEGER NOT NULL,
    supplier_id  INTEGER NOT NULL,
    product_image VARCHAR(255) NOT NULL
);

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE;

ALTER TABLE product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);
ALTER TABLE product ADD CONSTRAINT pk_product_id PRIMARY KEY (id);


CREATE TABLE supplier (
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE SEQUENCE supplier_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE;

ALTER TABLE supplier ALTER COLUMN id SET DEFAULT nextval('supplier_id_seq'::regclass);
ALTER TABLE supplier ADD CONSTRAINT pk_supplier_id PRIMARY KEY (id);


CREATE TABLE product_category (
    id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL
);

CREATE SEQUENCE product_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE;

ALTER TABLE product_category ALTER COLUMN id SET DEFAULT nextval('product_category_id_seq'::regclass);
ALTER TABLE product_category ADD CONSTRAINT pk_product_category_id PRIMARY KEY (id);


CREATE TABLE orders (
    id INTEGER NOT NULL,
    name VARCHAR(255),
    description VARCHAR(255),
    email VARCHAR(255),
    phone CHAR(15),
    billing_address VARCHAR(255),
    shipping_address VARCHAR(255),
    payment_method VARCHAR(255)
);


CREATE SEQUENCE orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE;

ALTER TABLE orders ALTER COLUMN id SET DEFAULT nextval('orders_id_seq'::regclass);
ALTER TABLE orders ADD CONSTRAINT pk_orders_id PRIMARY KEY (id);


CREATE TABLE order_products (
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL
);


ALTER TABLE product ADD CONSTRAINT fk_supplier_id FOREIGN KEY (supplier_id) REFERENCES supplier(id);
ALTER TABLE product ADD CONSTRAINT fk_product_category_id FOREIGN KEY (product_category_id) REFERENCES product_category(id);

ALTER TABLE order_products ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id);
ALTER TABLE order_products ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES product(id);