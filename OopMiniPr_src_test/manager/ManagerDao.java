package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 

public class ManagerDao {
	//db접속을 위한 변수 선언 
	private static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String user = "hr";
	private static final String pw = "tiger";
	private Connection conn;

	// 1.외부 클래스 또는 인스턴스에서 해당 클래스로 인스턴스를 생성하지 못하도록 처리
	private ManagerDao() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbcUrl, user, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Connection을 연결할 수 없습니다.");
			e.printStackTrace();
		}
	}

	// 2. 클래스 내부에서 인스턴스를 만들고
	// 유일한 인스턴스를 참조할 수 있는 자기자신 타입에 참조변수
	static private ManagerDao mDao;

	// 3. 메소드를 통해서 반환 하도록 처리
	public static ManagerDao getInstance() {
		if (mDao == null) {
			mDao = new ManagerDao();
		}
		return mDao;
	}

	// 로그아웃시 db와의 연결을 종료하는 메서드
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	// 매니저 로그인
	// manager 로그인 메서드
	public int managerLogin(String id, String Pw) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select mpw from manager where mid = ?";
		try {

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).contentEquals(Pw)) {
					System.out.println("관리자로 로그인 되었습니다.");

					return 1; // 로그인 성공
				}
			} else {
				System.out.println("아이디가 존재하지 않습니다.");
				return 0; // 아이디 존재 x
			}
			System.out.println("비밀번호 불일치");
			return -1; // 비밀번호 불일치

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("오류");
		return -2; // db 오류
	}

	// 멤버의 정보 삭제
	int deleteMember(int membercode) {
		int result = 0;

		PreparedStatement pstmt = null;

		String sql = "delete from member where membercode = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, membercode);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	

}