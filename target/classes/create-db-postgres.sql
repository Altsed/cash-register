DROP TABLE IF EXISTS warehouse, product, receipt_master, 
receipt_detail, user_account, user_role CASCADE;
DROP SEQUENCE IF EXISTS product_seq, user_role_seq, 
user_account_seq, warehouse_seq, receipt_master_seq, receipt_detail_seq CASCADE;


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
  product_id int NOT NULL,
  available_quantity int,
  available_weight real,
  PRIMARY KEY (id),
	CONSTRAINT FK_PRODUCT
	FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE SEQUENCE receipt_master_seq;
CREATE TABLE receipt_master (
  id int NOT NULL DEFAULT NEXTVAL ('receipt_master_seq'),
  date timestamp NOT NULL,
  user_id int NOT NULL,
  is_closed boolean DEFAULT false,
  	PRIMARY KEY (id),
	CONSTRAINT FK_USER
	FOREIGN KEY (user_id)
	REFERENCES user_account (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION

);

CREATE SEQUENCE receipt_detail_seq;
CREATE TABLE receipt_detail (
  id int NOT NULL DEFAULT NEXTVAL ('receipt_detail_seq'),
  product_id int NOT NULL,
  quantity int,
  weight real,
  receipt_master_id int NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT FK_PRODUCT
	FOREIGN KEY (product_id)
	REFERENCES product (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION,
	
	CONSTRAINT FK_RECEIPT_MASTER
	FOREIGN KEY (receipt_master_id)
	REFERENCES receipt_master (id)
	ON DELETE CASCADE ON UPDATE CASCADE
	
);

CREATE INDEX FK_USER_ROLE_idx ON user_account (role_id);
CREATE INDEX FK_PRODUCT_idx0 ON warehouse (product_id);
CREATE INDEX FK_PRODUCT_idx1 ON receipt_detail (product_id);
CREATE INDEX FK_RECEIPT_MASTER_idx ON receipt_detail (receipt_master_id);
CREATE INDEX FK_USER_idx ON receipt_master (user_id);

INSERT INTO product VALUES 
(1, 'apple', true);

INSERT INTO warehouse VALUES 
(1, 1, 0, 10.5);
INSERT INTO user_role VALUES 
(1, 'administrator');
INSERT INTO user_account VALUES 
(1, 'alex', 1, 1);
INSERT INTO receipt_master VALUES 
(1, '2020-09-17', 1, true);
INSERT INTO receipt_detail VALUES 
(1, 1, 5.2, 0, 1);


