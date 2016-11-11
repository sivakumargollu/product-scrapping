create database products;
use  products;
CREATE TABLE  product_info  (id  int(6) NOT NULL,name  varchar(30) NOT NULL, price  int(7) NOT NULL,search_query_id float(7,3) NOT NULL,PRIMARY KEY(id));
CREATE TABLE `search_quries` (
	`id` int(7) NOT NULL,
	`query` varchar(64) NOT NULL,
	PRIMARY KEY (`id`)
);

