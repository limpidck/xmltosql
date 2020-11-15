
DROP TABLE  IF EXISTS rd_test_xml_to_sql;
CREATE TABLE rd_test_xml_to_sql(
	id  int(11) NOT NULL AUTO_INCREMENT ,
	pay_channel VARCHAR(255) NOT NULL DEFAULT '' ,
	service VARCHAR(255) NOT NULL DEFAULT '' ,
	create_time timestamp NULL DEFAULT NULL ,
	trade_state int(11) NOT NULL DEFAULT 0 ,
	resp_code VARCHAR(255) NOT NULL DEFAULT '' ,
	resp_msg timestamp NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
