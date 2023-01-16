-- SQLINES DEMO *** ix_mobile;
create database fix_mobile;
use fix_mobile;

CREATE TABLE categories (
	id_category int NOT NULL auto_increment primary key,
    type int not null,
	name nvarchar(255)  null,
	status int null
) ;
CREATE TABLE capacity (
	id_capacity int NOT NULL auto_increment primary key,
	name nvarchar(100)  null
) ;
CREATE TABLE ram (
	id_ram int NOT NULL auto_increment primary key,
	name nvarchar(100)  null
) ;

CREATE TABLE color (
	id_color int NOT NULL auto_increment primary key,
	name nvarchar(100)  null
) ;
CREATE TABLE images (
	id_image int NOT NULL auto_increment primary key,
	name nvarchar(255)  null,
	id_product int null,
	id_product_change int null
) ;

CREATE TABLE roles (
	id_role int NOT NULL auto_increment primary key,
	name nvarchar(20)
) ;

CREATE TABLE accounts (
	username nvarchar(50) PRIMARY KEY not null,
	password nvarchar(100) not null,
	full_name nvarchar(100)  null,
	gender binary default(0)  null,
	email nvarchar(100) not null,
	phone nvarchar(20)  null,
	create_date date,
	image nvarchar(255) not null,
	status binary DEFAULT(0) not null,
	id_role int not null,
	address_id int NULL,
	foreign key(id_role) references roles(id_role)
);
CREATE TABLE address (
	id_address int NOT NULL auto_increment primary key,
   	address_detail nvarchar(255) not null,
	person_take nvarchar(50) not null,
	phone_take nvarchar(20) not null,
	id_province VARCHAR(40) NULL,
	id_district VARCHAR(40) NULL,
	id_ward VARCHAR(40) NULL,
	username nvarchar(50) not null,
	address_take VARCHAR(1000) NULL,
	district VARCHAR(1000) NULL,
	province VARCHAR(1000) NULL,
	ward VARCHAR(1000) NULL,
	foreign key(username) references accounts(username)
) ;

CREATE TABLE products (
	id_product int NOT NULL auto_increment primary key,
	name nvarchar(255)  NULL,
	create_date date  NULL,
	camera nvarchar(100)  NULL,
	price decimal(10,0)  NULL,
	size nvarchar(100)  NULL,
	note nvarchar(255),
	status int default(0),
	id_ram int  NULL,
	id_color int NOT  NULL,
	id_capacity int NOT NULL,
	id_category int NOT  NULL,
	id_image int  NULL,
	foreign key(id_ram) references ram(id_ram),
	foreign key(id_color) references color(id_color),
	foreign key(id_capacity) references capacity(id_capacity),
	foreign key(id_category) references categories(id_category),
	foreign key(id_image) references images(id_image)
) ;

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

CREATE TABLE sale (
	id_sale int NOT NULL auto_increment primary key,
	name nvarchar(100) ,
	type_sale nvarchar(100) ,
	create_start datetime ,
	create_end datetime,
	voucher nvarchar(100),
	value_min decimal(10,0),
	money_sale decimal(10,0),
	percent_sale int,
	quantity int,
	create_time datetime,
	update_time datetime ,
	user_update varchar(50) ,
	user_create varchar(50),
	detail_sale varchar(250),
	discount_method int,
	discount_type int,
	user_type int
) ;

CREATE TABLE sale_detail (
	id_detail int NOT NULL auto_increment primary key,
	id_sale int  NULL,
	id_product int  NULL,
	id_accessory int NULL,
	username nvarchar(50) null,
	foreign key(id_sale) references sale(id_sale)
);

CREATE TABLE orders (
	id_order int NOT NULL auto_increment primary key, 
	create_date datetime null,
	time_receive datetime null,
	total decimal(10,0)  null,
	note nvarchar(255) NULL,
	address nvarchar(255)  null,
	status int DEFAULT(0)  null,
	status_buy int null,
	type binary  null,
	username nvarchar(50)  NULL,
	money_ship decimal(10,0) null,
	person_take nvarchar(50)  NULL,
	phone_take nvarchar(15)  NULL,
	foreign key(username) references accounts(username)
);

CREATE TABLE order_detail (
	id_detail int NOT NULL auto_increment primary key, 
	quantity int  null,
	price decimal(10,0) null,
	status binary DEFAULT(0),
	price_sale decimal(10,0) NULL, 
	id_sale int NULL, 
	id_order int NULL, 
	id_product int NULL,
	id_accessory int NULL,
	foreign key(id_order) references orders(id_order),
	foreign key(id_product) references products(id_product),
	foreign key(id_accessory) references accessories(id_accessory)
);

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

CREATE TABLE product_change (
	id_change int NOT NULL auto_increment primary key,
	imei int  NULL, 
	date_change date  NULL,
	note nvarchar(255)  NULL,
	email varchar(255) null,
	status int default(0)  NULL,
	username nvarchar(50)  null,
	id_order_detail int null,
	quantity_change int null,
	foreign key(username) references accounts(username),
	foreign key(id_order_detail) references order_detail(id_detail)
);

CREATE TABLE change_detail (
	id_change_detail int NOT NULL auto_increment primary key,
	id_product int NOT NULL,
	id_order_detail int NOT NULL, 
	id_change int  NULL,
	foreign key(id_product) references products(id_product),
	foreign key(id_order_detail) references order_detail(id_detail),
	foreign key(id_change) references product_change(id_change)
);

CREATE TABLE imayproduct (
    id_imay int NOT NULL auto_increment primary key,
	name varchar(255),
	id_product int not null,
	id_detail int null,
	status int default(0), 
	foreign key(id_product) references products(id_product),
	foreign key(id_detail) references order_detail(id_detail)
);

CREATE TABLE apply_sale (
	id_applysale int NOT NULL auto_increment primary key, 
	id_sale int null,
	id_order int NULL, 
	user_apply nvarchar(100) NULL
);

insert into fix_mobile.roles(name) values ('ADMIN');
insert into fix_mobile.roles(name) values ('STAFF');
insert into fix_mobile.roles(name) values ('USER');
insert into fix_mobile.roles(name) values ('GUEST');
-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY

insert into fix_mobile.categories(type, name,status)
values (1,'Sạc',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Tai nghe dây',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Tai nghe không dây',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Ốp lưng IPhone 5',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Ốp lưng IPhone 6',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Ốp lưng IPhone 7',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Ốp lưng IPhone 8',1);
insert into fix_mobile.categories(type, name,status)
values (1,'Ốp lưng IPhone X',1);
insert into fix_mobile.categories(type, name,status)
values (0,'IPhone 5',1);
insert into fix_mobile.categories(type, name,status)
values (0,'IPhone 6',1);
insert into fix_mobile.categories(type, name,status)
values (0,'IPhone 7',1);
insert into fix_mobile.categories(type, name,status)
values (0,'IPhone 8',1);
insert into fix_mobile.categories(type, name,status)
values (0,'IPhone X',1);


-- SQLINES DEMO *** OR EVALUATION USE ONLY
-- SQLINES LICENSE FOR EVALUATION USE ONLY
INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, true, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, true, 'asdssaq1.jpg', 'Đẹp', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, true, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, true, '132122132221312w1e.jpg', 'Không mô tả', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, true, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('sadsasda', 22, '2022-10-06', '22', 2222, true, '61b57e26-3da9-4808-9a37-f70bb4f9cb40_rw_1920.png', '211', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('2132', 213213, '2022-10-06', '231213', 312213213, true, 'opchongvadap.jpg', '213123', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('123213', 321312321, '2022-10-06', '312312', 123123123, true, '1017.jpg', '123123', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('1322131', 23123123, '2022-10-06', '231123', 123123123, true, '1002.jpg', '123213', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, true, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, true, 'asdssaq1.jpg', 'Đẹp', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, true, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, true, '132122132221312w1e.jpg', 'Không mô tả', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, true, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('1322131', 23123123, '2022-10-06', '231123', 123123123, true, '1017.jpg', '123213', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Sạc giá sỉ XSMax', 213, '2022-10-04', 'Trắng', 200000, true, 'T2Uui2XkJXXXXXXXXX_761860821.jpg', 'Rẻ', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Ốp naruto', 123, '2022-10-04', 'Vàng', 75000, true, 'asdssaq1.jpg', 'Đẹp', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Tai nghe không dây', 200, '2022-10-04', 'Đen tuyền', 230000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category) 
VALUES ('Ốp mỏng', 500, '2022-10-04', 'Cam trắng', 120000, true, '2e21e21e221.jpg', 'Đẹp, hoa cam hình tròn', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Sạc không dây', 26, '2022-10-05', 'Đen', 2000000, true, '132122132221312w1e.jpg', 'Không mô tả', 2);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Ốp chống va đập', 21, '2022-10-05', 'Trắng, viền đen', 150000, true, 'opchongvadap.jpg', 'Loại ốp siêu bền', 3);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Tai nghe', 22, '2022-10-06', 'Đen', 200000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);

INSERT INTO fix_mobile.accessories (name, quantity, create_date, color, price, status, image, note, id_category)
VALUES ('Tai nghe', 22, '2022-10-06', 'Đen', 200000, true, 'tai-nghe-khong-day.jpg', 'Bền, đẹp mắt', 1);

INSERT INTO fix_mobile.ram (name) VALUES ('4G');
INSERT INTO fix_mobile.ram (name) VALUES ('6G');
INSERT INTO fix_mobile.ram (name) VALUES ('8G');

INSERT INTO fix_mobile.capacity (name) VALUES ('32GB');
INSERT INTO fix_mobile.capacity (name) VALUES ('64GB');
INSERT INTO fix_mobile.capacity (name) VALUES ('128GB');

INSERT INTO fix_mobile.color (name) VALUES ('Xanh');
INSERT INTO fix_mobile.color (name) VALUES ('Đỏ');
INSERT INTO fix_mobile.color (name) VALUES ('Tím');
INSERT INTO fix_mobile.color (name) VALUES ('Vàng');
INSERT INTO fix_mobile.color (name) VALUES ('Hồng');
INSERT INTO fix_mobile.color (name) VALUES ('Trắng');

INSERT INTO fix_mobile.images (name) VALUES ('1017.jpg');

INSERT INTO fix_mobile.products (name,create_date,camera,price,size,note,
				 status,id_ram,id_color,id_capacity,id_category,id_image) 
VALUES ('IPhone XS Max', '2022-10-06', '1280px, cảm biến LiDAR', 20000000, '20x25cm', 'Bền, đẹp mắt', 
	1,1,1,1,13,1);
select*from orders;

INSERT INTO fix_mobile.accounts(username,password,full_name,gender,email,
				status,phone,create_date,image,id_role) 
VALUES ('admin','$2a$12$nSbqR9l9BenII9tUcUoHXeDb77X.x59UZPMB894ynZ8z8I4WDSLOO','admin',0,'admin@gmail.com',
	1,'0912321321','2022/10/30','',1);
INSERT INTO fix_mobile.accounts(username,password,full_name,gender,email,
				status,phone,create_date,image,id_role) 
VALUES ('user','$2a$12$SYSb7NU1cND3wct0LRWrxe3id70DF6PeGbhkbcFATooYDKe4wyyqe','user',1,'user@gmail.com',
	1,'0123456789','2002/11/11','',3);
INSERT INTO fix_mobile.accounts(username,password,full_name,gender,email,
				status,phone,create_date,image,id_role) 
VALUES ('vietanhvs','$2a$12$FUNIidYXB/rc3BRR1XuQZObS4Vn7BPPomqllVvwcBOkJtZJWKFM16','Hạ Việt Anh',0,'vietanhvs@gmail.com',
	1,'0984297473','2022/10/30','',1);
	
CREATE OR REPLACE view customerorders as select o.id_order AS 'id_order',count(d.id_detail) AS 'totalquantity',count(o.id_order) AS 'totalorder',`a`.`full_name` AS 'full_name', a.create_date AS 'create_date',sum(o.total) AS 'totalmoney' from ((((`fix_mobile`.`accounts` `a` left join `fix_mobile`.`orders` `o` on(`a`.`username` = `o`.`username`)) left join `fix_mobile`.`order_detail` `d` on(`d`.`id_order` = `o`.`id_order`)) left join `fix_mobile`.`products` `p` on(`p`.`id_product` = `d`.`id_product`)) left join `fix_mobile`.`imayproduct` `i` on(`i`.`id_product` = `p`.`id_product`)) group by `o`.`id_order`,`a`.`username`,`p`.`id_product`;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getBigSale_ALLSHOP`(IN _money decimal)
begin
    declare max_moneySale decimal;
    declare max_percentSale integer;
    select MAX(money_sale) FROM sale where type_sale = 0 and discount_method = 1 and
            quantity != 0 and NOW() between create_start  and create_end
    into max_moneySale;
    select MAX(percent_sale) FROM sale where type_sale = 0 and discount_method = 1 and
            quantity != 0 and NOW() between create_start  and create_end
    into max_percentSale;
    if(max_moneySale is null) then
        select * from sale where percent_sale = max_percentSale and type_sale = 0 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    elseif (max_percentSale is null ) then
        select * from sale where money_sale = max_moneySale and type_sale = 0 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    elseif (max_moneySale > _money * max_percentSale/100) then
        select * from sale where money_sale = max_moneySale and type_sale = 0 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    else
        select * from sale where percent_sale = max_percentSale and type_sale = 0 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    end if;
END$$
DELIMITER ;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getBigSale_Accessory`(IN _idacsr int)
begin
    declare max_moneySale decimal;
    declare max_percentSale integer;
    declare _money decimal;

    select MAX(money_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
                           where type_sale = 4 and discount_method = 1 and id_accessory = _idacsr and
            quantity != 0 and NOW() between create_start  and create_end
    into max_moneySale;
    select MAX(percent_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
                             where type_sale = 4 and discount_method = 1 and id_accessory = _idacsr and
            quantity != 0 and NOW() between create_start  and create_end
    into max_percentSale;
    select price from accessories where accessories.id_accessory = _idacsr
    into _money;

    if(max_moneySale is null) then
        select * from sale where percent_sale = max_percentSale and type_sale = 4 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    ELSEIF (max_percentSale is null) then
        select * from sale where money_sale = max_moneySale and type_sale = 4 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    ELSEIF (max_moneySale > _money * max_percentSale/100) then
        select * from sale where money_sale = max_moneySale and type_sale = 4 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    else
        select * from sale where percent_sale = max_percentSale and type_sale = 4 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    end if;
END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getBigSale_Order`(IN _money decimal)
begin
    declare max_moneySale decimal;
    declare max_percentSale integer;
    select MAX(money_sale) FROM sale where type_sale = 2 and discount_method = 1 and _money > sale.value_min and
            quantity != 0 and NOW() between create_start  and create_end
    into max_moneySale;
    select MAX(percent_sale) FROM sale where type_sale = 2 and discount_method = 1 and _money > sale.value_min and
            quantity != 0 and NOW() between create_start  and create_end
    into max_percentSale;
    if(max_moneySale is null) then
        select * from sale where percent_sale = max_percentSale and type_sale = 2 and discount_method = 1 and _money > sale.value_min and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    elseif (max_percentSale is null ) then
        select * from sale where money_sale = max_moneySale and type_sale = 2 and discount_method = 1 and _money > sale.value_min and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    elseif (max_moneySale > _money * max_percentSale/100) then
        select * from sale where money_sale = max_moneySale and type_sale = 2 and discount_method = 1 and _money > sale.value_min and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    else
        select * from sale where percent_sale = max_percentSale and type_sale = 2 and discount_method = 1 and _money > sale.value_min and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    end if;
END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getBigSale_Products`(IN _idprd int)
begin
    declare max_moneySale decimal;
    declare max_percentSale integer;
    declare _money decimal;

    select MAX(money_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
                           where type_sale = 1 and discount_method = 1 and id_product = _idprd and
            quantity != 0 and NOW() between create_start  and create_end
    into max_moneySale;
    select MAX(percent_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
                             where type_sale = 1 and discount_method = 1 and id_product = _idprd and
            quantity != 0 and NOW() between create_start  and create_end
    into max_percentSale;
    select price from products where id_product = _idprd
    into _money;

    if(max_moneySale is null) then
        select * from sale where percent_sale = max_percentSale and type_sale = 1 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    ELSEIF (max_percentSale is null) then
        select * from sale where money_sale = max_moneySale and type_sale = 1 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    ELSEIF (max_moneySale > _money * max_percentSale/100) then
        select * from sale where money_sale = max_moneySale and type_sale = 1 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    else
        select * from sale where percent_sale = max_percentSale and type_sale = 1 and discount_method = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    end if;
END$$
DELIMITER ;



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getBigSale_UserName`(IN _userName varchar(50), IN _money decimal)
begin
    declare max_moneySale decimal;
    declare max_percentSale integer;

    if ((select username from orders where username like _userName limit 1) is null) then
        select MAX(money_sale) FROM sale s where type_sale = 3 and discount_method = 1 and user_type = 0 and
                quantity != 0 and NOW() between create_start  and create_end
        into max_moneySale;
        select MAX(percent_sale) FROM sale s where type_sale = 3 and discount_method = 1 and user_type = 0 and
                quantity != 0 and NOW() between create_start  and create_end
        into max_percentSale;
    end if;
    if (max_moneySale is null) then
        select MAX(money_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
        where type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                quantity != 0 and NOW() between create_start  and create_end
        into max_moneySale;
    else
        if(max_moneySale<(select MAX(money_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
                          where type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                                  quantity != 0 and NOW() between create_start  and create_end)) then
            select MAX(money_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
            where type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                    quantity != 0 and NOW() between create_start  and create_end
            into max_moneySale;
        end if;
    end if;

    if (max_percentSale is null) then
        select MAX(percent_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
        where type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                quantity != 0 and NOW() between create_start  and create_end
        into max_percentSale;
    else
        if (max_percentSale<(select MAX(percent_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
                             where type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                                     quantity != 0 and NOW() between create_start  and create_end)) then
            select MAX(percent_sale) FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
            where type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                    quantity != 0 and NOW() between create_start  and create_end
            into max_percentSale;
        end if;
    end if;

    if(max_moneySale is null) then
        select * FROM sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
        where percent_sale = max_percentSale and type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    ELSEIF (max_percentSale is null) then
        select * from sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
        where money_sale = max_moneySale and type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    ELSEIF (max_moneySale > _money * max_percentSale/100) then
        select * from sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
        where money_sale = max_moneySale and type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    else
        select * from sale s LEFT join sale_detail sd ON s.id_sale = sd.id_sale
        where percent_sale = max_percentSale and type_sale = 3 and discount_method = 1 and username = _userName and user_type = 1 and
                quantity != 0 and NOW() between create_start  and create_end limit 1;
    end if;
END$$
DELIMITER ;


