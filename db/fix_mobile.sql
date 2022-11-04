-- SQLINES DEMO *** ix_mobile;
-- create database fix_mobile;
use fix_mobile;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE categories (
	id_category int NOT NULL auto_increment primary key,
    type binary not null,
	name nvarchar(255) not null
) ;
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE capacity (
	id_capacity int NOT NULL auto_increment primary key,
	name nvarchar(100) not null
) ;
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE ram (
	id_ram int NOT NULL auto_increment primary key,
	name nvarchar(100) not null
) ;
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE color (
	id_color int NOT NULL auto_increment primary key,
	name nvarchar(100) not null
) ;
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE images (
	id_image int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE province (
	id_province int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE district (
	id_district int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE commune (
	id_commune int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE roles (
	id_role int NOT NULL auto_increment primary key,
	name nvarchar(20)
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE accounts (
	username nvarchar(50) PRIMARY KEY not null,
	password nvarchar(100) not null,
    full_name nvarchar(100) not null,
	gender binary default(0) not null,
	email nvarchar(100) not null,
	phone nvarchar(20) not null,
	create_date date,
	image nvarchar(255) not null,
	status binary DEFAULT(0) not null,
	id_role int not null,
	foreign key(id_role) references roles(id_role)
) ;
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE address (
	id_address int NOT NULL auto_increment primary key,
    address_detail nvarchar(255) not null,
    person_take nvarchar(50) not null,
    phone_take nvarchar(20) not null,
	id_province int NOT NULL,
	id_district int NOT NULL,
	id_commune int NOT NULL,
	username nvarchar(50) not null,
	foreign key(username) references accounts(username),
	foreign key(id_province) references province(id_province),
	foreign key(id_district) references district(id_district),
	foreign key(id_commune) references commune(id_commune)
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE products (
	id_product int NOT NULL auto_increment primary key,
	name nvarchar(255) NOT NULL,
	create_date date NOT NULL,
	camera nvarchar(100) NOT NULL,
	price decimal(10,0) NOT NULL,
	size nvarchar(100) NOT NULL,
	note nvarchar(255),
	status int  default(0),
	id_ram int NOT NULL,
	id_color int NOT NULL,
	id_capacity int NOT NULL,
	id_category int NOT NULL,
	id_image int NOT NULL,
	foreign key(id_ram) references ram(id_ram),
	foreign key(id_color) references color(id_color),
	foreign key(id_capacity) references capacity(id_capacity),
	foreign key(id_category) references categories(id_category),
	foreign key(id_image) references images(id_image)
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE accessories (
	id_accessory int NOT NULL auto_increment primary key,
	name nvarchar(255) NOT NULL,
	quantity int NOT NULL,
	create_date date NOT NULL,
	color nvarchar(100) not null,
	price decimal(10,0) not null,
	status binary default(0),
	image nvarchar(255),
	note nvarchar(255),
	id_category int NOT NULL ,
	foreign key(id_category) references categories(id_category)
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE sale (
	id_sale int NOT NULL auto_increment primary key,
	name nvarchar(100) NOT NULL,
	type_sale nvarchar(100) NOT NULL,
	create_start date NOT NULL,
	create_end date NOT NULL,
	voucher nvarchar(100) NOT NULL,
	value_min decimal(10,0) NOT NULL,
	money_sale decimal(10,0) NOT NULL,
	quantity_use int NOT NULL,
	status binary DEFAULT(0)
) ;

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE sale_detail (
	id_detail int NOT NULL auto_increment primary key,
	id_sale int NOT NULL,
	id_product int NOT NULL,
	foreign key(id_sale) references sale(id_sale),
	foreign key(id_product) references products(id_product)
);

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE orders (
	id_order int NOT NULL auto_increment primary key, 
	create_date date not null,
	total decimal(10,0) not null,
	note nvarchar(255) NULL,
	address nvarchar(255) not null,
	status binary DEFAULT(0) not null,
	type binary not null,
	username nvarchar(50) NOT NULL,
	foreign key(username) references accounts(username)
);

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE order_detail (
	id_detail int NOT NULL auto_increment primary key, 
	quantity int not null,
	price decimal(10,0) not null,
	status binary DEFAULT(0),
	id_order int NOT NULL, 
	id_product int NULL,
	id_accessory int NULL,
	foreign key(id_order) references orders(id_order),
	foreign key(id_product) references products(id_product),
	foreign key(id_accessory) references accessories(id_accessory)
);

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE insurance (
	id_insurance int NOT NULL auto_increment primary key, 
	name int not null,
	date_start date NOT NULL,
	date_end date NOT NULL,
    quantity int not null,
    price decimal(10,0) NOT NULL
);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE insurance_detail(
	id_detail int NOT NULL auto_increment primary key,
    id_insurance int NOT NULL, 
    id_product int NOT NULL,
	username nvarchar(50) not null,
	foreign key(id_insurance) references insurance(id_insurance),
	foreign key(id_product) references products(id_product),
	foreign key(username) references accounts(username)
);

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE product_return (
	id_return int NOT NULL auto_increment primary key, 
	date_return date NOT NULL, 
	price decimal(10,0) NOT NULL,
	note nvarchar(255) NOT NULL,
	id_detail int NOT NULL,
	id_product int NOT NULL,
	foreign key(id_detail) references order_detail(id_detail),
	foreign key(id_product) references products(id_product)
);

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE product_change (
	id_change int NOT NULL auto_increment primary key,
	imei int NOT NULL, 
	data_change int NOT NULL,
	note nvarchar(255) NOT NULL,
	price decimal(10,0) NOT NULL,
	status binary default(0) NOT NULL,
	username nvarchar(50) not null,
	foreign key(username) references accounts(username)
);

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE change_detail (
	id_change_detail int NOT NULL auto_increment primary key,
	id_product int NOT NULL,
	id_order_detail int NOT NULL, 
	id_change int NOT NULL,
	foreign key(id_product) references products(id_product),
	foreign key(id_order_detail) references order_detail(id_detail),
	foreign key(id_change) references product_change(id_change)
);
-- SQLINES LICENSE FOR EVALUATION USE ONLY
CREATE TABLE imayproduct (
    id_imay int NOT NULL auto_increment primary key,
	name varchar(255),
	id_product int not null,
	status int default(0), 
	foreign key(id_product) references products(id_product)
);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.roles(name)
values ('ADMIN');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.roles(name)
values ('STAFF');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.roles(name)
values ('USER');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.roles(name)
values ('GUEST');

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Sạc');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Tai nghe dây');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Tai nghe không dây');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Ốp lưng IPhone 5');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Ốp lưng IPhone 6');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Ốp lưng IPhone 7');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Ốp lưng IPhone 8');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (1,'Ốp lưng IPhone X');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (0,'IPhone 5');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (0,'IPhone 6');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (0,'IPhone 7');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (0,'IPhone 8');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
insert into fix_mobile.categories(type, name)
values (0,'IPhone X');

-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, true, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, true, 'asdssaq1.jpg', 'Đẹp', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, true, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, true, '132122132221312w1e.jpg', 'Không mô tả', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, true, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('sadsasda', 22, '2022-10-06', '22', 2222, true, '61b57e26-3da9-4808-9a37-f70bb4f9cb40_rw_1920.png', '211', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('2132', 213213, '2022-10-06', '231213', 312213213, true, 'opchongvadap.jpg', '213123', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('123213', 321312321, '2022-10-06', '312312', 123123123, true, '1017.jpg', '123123', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('1322131', 23123123, '2022-10-06', '231123', 123123123, true, '1002.jpg', '123213', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, true, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, true, 'asdssaq1.jpg', 'Đẹp', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, true, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, true, '132122132221312w1e.jpg', 'Không mô tả', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, true, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('1322131', 23123123, '2022-10-06', '231123', 123123123, true, '1017.jpg', '123213', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, true, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, true, 'asdssaq1.jpg', 'Đẹp', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE true
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, true, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, true, '132122132221312w1e.jpg', 'Không mô tả', 2);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, true, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Tai nghe', 22, '2022-10-06', 'Đen', 200000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) VALUES ('Tai nghe', 22, '2022-10-06', 'Đen', 200000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);

INSERT INTO fix_mobile.accounts(username,password,full_name,gender,email,
								phone,create_date,image,id_role) 
VALUES ('admin','123','admin',0,'admin@gmail.com','0912321321','2022/10/30','',1);
