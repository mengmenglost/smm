package org.mm.db;

import java.sql.SQLException;

public class test {
	public static void main(String[] args) throws SQLException {
		String url="jdbc:mysql://st-mysql-m.a.pa.com:3306/aazb2c_db?useSSL=false";
		String user="qaadmin";
		String password="l4misO9qqn";
		DbOperator dbOperator=new DbOperator(url, user, password);
		dbOperator.getList("SELECT * FROM `t_housing` LIMIT 5");
	}
}
