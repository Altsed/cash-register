DROP TABLE IF EXISTS warehouse, product, receipt,
receipt_has_product, user_account, user_role CASCADE;
DROP SEQUENCE IF EXISTS product_seq, user_role_seq, 
user_account_seq, warehouse_seq, receipt_seq CASCADE;


CREATE SEQUENCE product_seq;
CREATE TABLE product (
  id int NOT NULL DEFAULT NEXTVAL ('product_seq'),
  name varchar(64) UNIQUE NOT NULL,
  is_weight boolean DEFAULT FALSE,
  PRIMARY KEY (id)
);
CREATE SEQUENCE user_role_seq;
CREATE TABLE user_role (
  id int NOT NULL DEFAULT NEXTVAL ('user_role_seq'),
  name varchar(64) UNIQUE NOT NULL,
  PRIMARY KEY (id)
);
CREATE SEQUENCE user_account_seq;
CREATE TABLE user_account (
  id int NOT NULL DEFAULT NEXTVAL ('user_account_seq'),
  login varchar(64) UNIQUE NOT NULL,
  password varchar(64) NOT NULL,
  role_id int NOT NULL,
  PRIMARY KEY (id),
	CONSTRAINT FK_USER_ROLE
	FOREIGN KEY (role_id)
	REFERENCES user_role (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE SEQUENCE warehouse_seq;
CREATE TABLE warehouse (
  id int NOT NULL DEFAULT NEXTVAL ('warehouse_seq'),
  product_id int NOT NULL UNIQUE NOT NULL ,
  available_quantity real,
  PRIMARY KEY (id),
	CONSTRAINT FK_PRODUCT
	FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE SEQUENCE receipt_seq;
CREATE TYPE receipt_status AS ENUM ('open', 'closed', 'canceled');
CREATE TABLE receipt (
  id int NOT NULL DEFAULT NEXTVAL ('receipt_seq'),
  user_id int NOT NULL,
  is_closed boolean DEFAULT false,
  status receipt_status DEFAULT 'open';
  	PRIMARY KEY (id),
	CONSTRAINT FK_USER
	FOREIGN KEY (user_id)
	REFERENCES user_account (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION

);


CREATE TABLE receipt_has_product (
  receipt_id int NOT NULL,
  product_id int NOT NULL,
  quantity int,
  weight real,
  PRIMARY KEY (product_id),
	CONSTRAINT FK_PRODUCT
	FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION,
	
	CONSTRAINT FK_RECEIPT
	FOREIGN KEY (receipt_id)
	REFERENCES receipt (id)
	ON DELETE CASCADE ON UPDATE CASCADE
	
);

CREATE INDEX FK_USER_ROLE_idx ON user_account (role_id);
CREATE INDEX FK_PRODUCT_idx0 ON warehouse (product_id);
CREATE INDEX FK_PRODUCT_idx1 ON receipt_has_product (product_id);
CREATE INDEX FK_RECEIPT_idx ON receipt_has_product (receipt_id);
CREATE INDEX FK_USER_idx ON receipt (user_id);

INSERT INTO product (name, is_weight) VALUES
('apple', true);

INSERT INTO warehouse (product_id, available_quantity, available_weight) VALUES
(1, 0, 10.5);
INSERT INTO user_role (name) VALUES
('administrator');
INSERT INTO user_account (login, password, role_id) VALUES
('alex', 1, 1);
INSERT INTO receipt (user_id, is_closed) VALUES
( 1, true);
INSERT INTO receipt_has_product (receipt_id, product_id, quantity, weight) VALUES
(1, 1, 0, 5.2);
