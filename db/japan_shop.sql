
use japan_shop;

CREATE TABLE categories (
	id_category int NOT NULL auto_increment primary key,
    type bit not null,
	name nvarchar(255) not null
) ;
CREATE TABLE capacity (
	id_capacity int NOT NULL auto_increment primary key,
	name nvarchar(100) not null
) ;
CREATE TABLE ram (
	id_ram int NOT NULL auto_increment primary key,
	name nvarchar(100) not null
) ;
CREATE TABLE color (
	id_color int NOT NULL auto_increment primary key,
	name nvarchar(100) not null
) ;
CREATE TABLE images (
	id_image int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

CREATE TABLE province (
	id_province int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

CREATE TABLE district (
	id_district int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

CREATE TABLE commune (
	id_commune int NOT NULL auto_increment primary key,
	name nvarchar(255) not null
) ;

CREATE TABLE roles (
	id_role int NOT NULL auto_increment primary key,
	name nvarchar(20)
) ;

CREATE TABLE accounts (
	username nvarchar(50) PRIMARY KEY not null,
	password nvarchar(100) not null,
    full_name nvarchar(100) not null,
	gender bit default(0) not null,
	email nvarchar(100) not null,
	phone nvarchar(20) not null,
	create_date date,
	image nvarchar(255) not null,
	status bit DEFAULT(0) not null,
	id_role int not null,
	foreign key(id_role) references roles(id_role)
) ;
INSERT INTO `japan_shop`.`accessories` (`id_accessory`, `name`, `quantity`, `create_date`, `color`, `price`, `status`, `image`, `note`, `id_category`) 
VALUES ('1', 'Tai nghe không dây', '200', '2022-10-04', 'Trắng', '1', 0, 'asdas', 'sad', '2');

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

CREATE TABLE products (
	id_product int NOT NULL auto_increment primary key,
	name nvarchar(255) NOT NULL,
	quantity int NOT NULL,
	create_date date NOT NULL,
	camera nvarchar(100) NOT NULL,
	price decimal NOT NULL,
	size nvarchar(100) NOT NULL,
	note nvarchar(255),
	status bit default(0),
	id_ram int NOT NULL,
	id_color int NOT NULL,
	id_capacity int NOT NULL,
	id_category int NOT NULL,
	foreign key(id_ram) references ram(id_ram),
	foreign key(id_color) references color(id_color),
	foreign key(id_capacity) references capacity(id_capacity),
	foreign key(id_category) references categories(id_category)
) ;

CREATE TABLE image_detail (
	id_detail int NOT NULL auto_increment primary key,
	id_image int NOT NULL,
	id_product int NOT NULL,
	foreign key(id_image) references images(id_image),
	foreign key(id_product) references products(id_product)
) ;
CREATE TABLE imei (
	id_imei int NOT NULL auto_increment primary key,
	id_product int NOT NULL,
	foreign key(id_product) references products(id_product)
) ;
CREATE TABLE accessories (
	id_accessory int NOT NULL auto_increment primary key,
	name nvarchar(255) NOT NULL,
	quantity int NOT NULL,
	create_date date NOT NULL,
	color nvarchar(100) not null,
	price decimal not null,
	status bit default(0),
	image nvarchar(255),
	note nvarchar(255),
	id_category int NOT NULL ,
	foreign key(id_category) references categories(id_category)
) ;

CREATE TABLE sale (
	id_sale int NOT NULL auto_increment primary key,
	name nvarchar(100) NOT NULL,
	type_sale nvarchar(100) NOT NULL,
	create_start date NOT NULL,
	create_end date NOT NULL,
	voucher nvarchar(100) NOT NULL,
	value_min decimal NOT NULL,
	money_sale decimal NOT NULL,
	quantity_use int NOT NULL,
	status bit DEFAULT(0)
) ;

CREATE TABLE sale_detail (
	id_detail int NOT NULL auto_increment primary key,
	id_sale int NOT NULL,
	id_product int NOT NULL,
	foreign key(id_sale) references sale(id_sale),
	foreign key(id_product) references products(id_product)
);

CREATE TABLE orders (
	id_order int NOT NULL auto_increment primary key, 
	create_date date not null,
	total decimal not null,
	note nvarchar(255) NOT NULL,
	status bit DEFAULT(0) not null,
	address nvarchar(255) not null,
	type bit not null,
	username nvarchar(50) NOT NULL,
	foreign key(username) references accounts(username)
);

CREATE TABLE order_detail_product (
	id_detail int NOT NULL auto_increment primary key, 
	quantity int not null,
	price decimal not null,
	status bit DEFAULT(0),
	id_order int NOT NULL, 
	id_product int NOT NULL, 
	foreign key(id_order) references orders(id_order),
	foreign key(id_product) references products(id_product)
);

CREATE TABLE order_detail_accessory (
	id_detail int NOT NULL auto_increment primary key, 
	quantity int not null,
	price decimal not null,
	status bit DEFAULT(0),
	id_order int NOT NULL, 
	id_accessory int NOT NULL, 
	foreign key(id_order) references orders(id_order),
	foreign key(id_accessory) references accessories(id_accessory)
);
CREATE TABLE insurance (
	id_insurance int NOT NULL auto_increment primary key, 
	name int not null,
	date_start date NOT NULL,
	date_end date NOT NULL,
    quantity int not null,
    price decimal NOT NULL
);
CREATE TABLE insurance_detail(
	id_detail int NOT NULL auto_increment primary key,
    id_insurance int NOT NULL, 
    id_product int NOT NULL,
	username nvarchar(50) not null,
	foreign key(id_insurance) references insurance(id_insurance),
	foreign key(id_product) references products(id_product),
	foreign key(username) references accounts(username)
);
CREATE TABLE insurance_product (
	id_insurance int NOT NULL auto_increment primary key, 
	note nvarchar(255) NOT NULL,
	create_start date NOT NULL,
	create_end date NOT NULL,
	status bit DEFAULT(0),
	id_detail int NOT NULL , 
	foreign key(id_detail) references order_detail_product(id_detail)
);

CREATE TABLE product_return (
	id_return int NOT NULL auto_increment primary key, 
	id_detail int NOT NULL,
	id_product int NOT NULL,
	foreign key(id_detail) references order_detail_product(id_detail),
	foreign key(id_product) references products(id_product)
);

CREATE TABLE return_detail (
	id_detail int NOT NULL auto_increment primary key,
	date_return date NOT NULL, 
	price decimal NOT NULL,
	note nvarchar(255) NOT NULL,
	id_return int NOT NULL,
	foreign key(id_return) references product_return(id_return)
);
CREATE TABLE product_change (
	id_change int NOT NULL auto_increment primary key,
	imei int NOT NULL, 
	data_change int NOT NULL,
	note nvarchar(255) NOT NULL,
	price decimal NOT NULL,
	status bit default(0) NOT NULL,
	username nvarchar(50) not null,
	foreign key(username) references accounts(username)
);

CREATE TABLE change_detail (
	id_change_detail int NOT NULL auto_increment primary key,
	id_product int NOT NULL,
	id_order_detail int NOT NULL, 
	id_change int NOT NULL,
	foreign key(id_product) references products(id_product),
	foreign key(id_order_detail) references order_detail_product(id_detail),
	foreign key(id_change) references product_change(id_change)
);
insert into japan_shop.roles(name)
values ('ADMIN');
insert into japan_shop.roles(name)
values ('STAFF');
insert into japan_shop.roles(name)
values ('USER');
insert into japan_shop.roles(name)
values ('GUEST');

insert into japan_shop.categories(type, name)
values (0,'IPhone 5');
insert into japan_shop.categories(type, name)
values (0,'IPhone 6');
insert into japan_shop.categories(type, name)
values (0,'IPhone 7');
insert into japan_shop.categories(type, name)
values (0,'IPhone 8');
insert into japan_shop.categories(type, name)
values (0,'IPhone X');
insert into japan_shop.categories(type, name)
values (1,'Tai nghe');
insert into japan_shop.categories(type, name)
values (1,'Sạc');
insert into japan_shop.categories(type, name)
values (1,'Ốp lưng IPhone X');

INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (1, 'Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, false, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (2, 'Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, false, 'asdssaq1.jpg', 'Đẹp', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (4, 'Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, false, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (17, 'Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, false, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (18, 'Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, false, '132122132221312w1e.jpg', 'Không mô tả', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (19, 'Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, false, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (20, 'sadsasda', 22, '2022-10-06', '22', 2222, false, '61b57e26-3da9-4808-9a37-f70bb4f9cb40_rw_1920.png', '211', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (21, '2132', 213213, '2022-10-06', '231213', 312213213, false, 'opchongvadap.jpg', '213123', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (22, '123213', 321312321, '2022-10-06', '312312', 123123123, false, '1017.jpg', '123123', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (23, '1322131', 23123123, '2022-10-06', '231123', 123123123, false, '1002.jpg', '123213', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (24, 'Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, false, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (25, 'Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, false, 'asdssaq1.jpg', 'Đẹp', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (26, 'Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, false, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (27, 'Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, false, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (28, 'Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, false, '132122132221312w1e.jpg', 'Không mô tả', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (29, 'Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, false, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (33, '1322131', 23123123, '2022-10-06', '231123', 123123123, false, '1017.jpg', '123213', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (34, 'Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, false, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (35, 'Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, false, 'asdssaq1.jpg', 'Đẹp', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (36, 'Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, false, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (37, 'Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, false, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (38, 'Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, false, '132122132221312w1e.jpg', 'Không mô tả', 2);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (39, 'Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, false, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (41, 'Tai nghe', 22, '2022-10-06', 'Đen', 200000, false, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
INSERT INTO japan_shop.accessories (id_accessory, name, quantity, create_date, color, price, status, image, note, id_category) VALUES (42, 'Tai nghe', 22, '2022-10-06', 'Đen', 200000, false, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);
