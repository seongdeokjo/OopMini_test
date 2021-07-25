package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConn {
	//싱글톤 패턴 사용
	private DbConn() {}
	static private DbConn dbConn;
	// 3. 메소드를 통해서 반환 하도록 처리
	public static DbConn getInstance() {
		if (dbConn == null) {
			dbConn = new DbConn();
		}
		return dbConn;
	}
	
	public static Connection getConnecting()  {
		Connection conn = null;
		String driver ="com.mysql.cj.jdbc.Driver";
		String jdbcUrl ="jdbc:mysql://localhost/rent?serverTimezone=UTC";
		String user = "testRent";
		String pw ="1234";
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(jdbcUrl, user, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;		
	}
}
