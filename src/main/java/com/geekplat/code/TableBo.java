package com.geekplat.code;

import java.util.Map;
import java.util.Set;
/**
 * 表工具类，转换并打印表定义sql语句
 * @author geekplat.com
 *
 */
public class TableBo {

	//表名
	private String tableName;

	//字段名及类型
	private Map<String, String> columns;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("DROP TABLE  IF EXISTS " + tableName + ";");
		sb.append("\r\n");
		sb.append("CREATE TABLE ");
		sb.append(tableName);
		sb.append("(");
		sb.append("\r\n");
		Set<Map.Entry<String, String>> set = columns.entrySet();
		int size = set.size();
		int i = 0;
		for (Map.Entry<String, String> entry : set) {
			i++;
			if (entry.getKey().equals("id")) {
				//字段名id代表主键
				sb.append("\t" + entry.getKey() + "  int(11) NOT NULL AUTO_INCREMENT");
			} else {
				//其它字段format格式化类型
				sb.append("\t" + entry.getKey() + " " + format(entry.getValue()));
			}
			if (size != i) {
				sb.append(" ,");
				sb.append("\r\n");
			}

		}
		sb.append(",\r\n");
		sb.append("\t" + "PRIMARY KEY (`id`) USING BTREE\r\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
		sb.append("\r\n");
		return sb.toString();

	}

	/**
	 * 格式化类型,可根据自己需要调整补充
	 * @param val xml中jdbcType类型定义
	 * @return 数据库对应类型
	 */
	private String format(String val) {
		if (val.equals("VARCHAR")) {
			return "VARCHAR(255) NOT NULL DEFAULT ''";
		}
		if (val.equals("INTEGER")) {
			return "int(11) NOT NULL DEFAULT 0";
		}
		if (val.equals("BIGINT")) {
			return "bigint(20) NOT NULL DEFAULT 0";
		}
		if (val.equals("LONG")) {
			return "bigint(20) NOT NULL DEFAULT 0";
		}
		if (val.equals("BIT")) {
			return "bit(1) NOT NULL DEFAULT 0";
		}
		if (val.equals("FLOAT")) {
			return "decimal(20, 4) NOT NULL DEFAULT 0";
		}
		if (val.equals("DECIMAL")) {
			return "decimal(20, 4) NOT NULL DEFAULT 0";
		}
		if (val.equals("LONGVARCHAR")) {
			return "text";
		}
		if (val.equals("TIMESTAMP")) {
			return "timestamp NULL DEFAULT NULL";
		}
		return val;
	}

}