package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCFactory {
	protected  static Connection conn;
	public static Connection connecting() {
		final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		final String user = "hr";
		final String pw = "tiger";
		conn = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, user, pw);
		} catch (SQLException e) {
			
		}	
		return conn;
	}
	public static void close(Statement statement) {
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
