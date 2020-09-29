DROP TABLE IF EXISTS warehouse, product, receipt,
receipt_has_product, user_account, user_role CASCADE;
DROP SEQUENCE IF EXISTS product_seq, user_role_seq, 
user_account_seq, warehouse_seq, receipt_seq, receipt_has_product_seq CASCADE;
DROP TYPE IF EXISTS receipt_status;


CREATE SEQUENCE product_seq;
CREATE TABLE product (
  id int NOT NULL DEFAULT NEXTVAL ('product_seq'),
  name varchar(64) UNIQUE NOT NULL,
  is_weight boolean DEFAULT FALSE,
  reference varchar(64) UNIQUE NOT NULL,
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
  status receipt_status DEFAULT 'open',
  	PRIMARY KEY (id),
	CONSTRAINT FK_USER
	FOREIGN KEY (user_id)
	REFERENCES user_account (id)
	ON DELETE NO ACTION ON UPDATE NO ACTION

);

CREATE SEQUENCE receipt_has_product_seq;
CREATE TABLE receipt_has_product (
  id int NOT NULL DEFAULT NEXTVAL ('receipt_has_product_seq'),
  receipt_id int NOT NULL,
  product_id int NOT NULL,
  quantity real,
  PRIMARY KEY (id),
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

INSERT INTO product (name, is_weight, reference) VALUES
('apple', true, 'code1'), ('beer', false, 'beer1');
INSERT INTO warehouse (product_id, available_quantity) VALUES
(1, 10.5), (2, 100);
INSERT INTO user_role (name) VALUES
('stockman'), ('operator'), ('chief_operator');
INSERT INTO user_account (login, password, role_id) VALUES
('1', '$2a$10$sZw/WJ3EXaqBpQGCNh7EZ.PPGTWcpElVzLMAo.MUvwjmZd8YyIMF6', 1),
('2', '$2a$10$N7405JapTpACBBmNbDxkA.A58MLrOR8jXJxEpOGSlwxRPPc2E9A2u', 2),
('3', '$2a$10$KYOrZKwLkrr1NoH7y0wYUO7eeHbUiWZvKIwbdD5VCknrOL1ZbAEGW', 3);

INSERT INTO receipt (user_id) VALUES
(1);
INSERT INTO receipt_has_product (receipt_id, product_id, quantity) VALUES
(1, 1, 5.2), (1, 2, 5);

CREATE OR REPLACE FUNCTION subtract_quantity_from_stock() RETURNS TRIGGER AS $$
DECLARE
    actual_quantity real;
    new_quantity real;
BEGIN
    SELECT available_quantity FROM warehouse WHERE warehouse.product_id = NEW.product_id INTO actual_quantity;

    IF  TG_OP = 'INSERT' THEN
       new_quantity = actual_quantity - NEW.quantity;
        UPDATE warehouse SET available_quantity=new_quantity WHERE product_id=NEW.product_id;
        RETURN NEW;
    ELSIF TG_OP = 'UPDATE' THEN
        new_quantity = actual_quantity - (NEW.quantity - OLD.quantity);
        UPDATE warehouse SET available_quantity=new_quantity WHERE product_id=NEW.product_id;
        RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
        new_quantity = actual_quantity + OLD.quantity ;
        UPDATE warehouse SET available_quantity=new_quantity WHERE product_id=NEW.product_id;
        RETURN OLD;
    END IF;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS t_quantity ON receipt_has_product;
CREATE TRIGGER t_quantity
    AFTER INSERT OR UPDATE OR DELETE ON receipt_has_product FOR EACH ROW EXECUTE PROCEDURE subtract_quantity_from_stock();
