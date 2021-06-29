package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectiong {
	private static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String user = "hr";
	private static final String pw = "tiger";
	
	public static Connection connecting() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, user, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return conn;
	}
}
