package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TinyInt {
// tinyInt(1) --> java 에서 boolean,int 가능
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String jdbcUrl ="jdbc:mysql://localhost/rent?serverTimezone=UTC";
			String uesr = "testRent";
			String pw ="1234";
			int result = 0;
			Connection conn = DriverManager.getConnection(jdbcUrl, uesr, pw);
		
			String sql = "update pay set payCk = ? where idx = 2";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,0);
			
			result = pstmt.executeUpdate();
			
			if(result >0) {
				System.out.println("변경성공");
			}else {
				System.out.println("실패!");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
