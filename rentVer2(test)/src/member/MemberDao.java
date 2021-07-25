package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import util.DbClose;


public class MemberDao {
	// 1.외부 클래스 또는 인스턴스에서 해당 클래스로 인스턴스를 생성하지 못하도록 처리
	private MemberDao() {}
	static private MemberDao mbDao;

	// 3. 메소드를 통해서 반환 하도록 처리
	public static MemberDao getInstance() {
		if (mbDao == null) {
			mbDao = new MemberDao();
		}
		return mbDao;
	}
	//회원 가입 - create
	public int insertMember(Connection conn,Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlInsert ="insert into member (memberId,memberPw,memberName,regNumber,account) values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getRegNumber());
			pstmt.setString(5, member.getAccount());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		return result;
	}
	
	//회원 전체 리스트 - read
	public ArrayList<Member> getMemberList(Connection conn){
		ArrayList<Member> list = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from member order by idx";
			rs = stmt.executeQuery(sql);
			
			list = new ArrayList<Member>();
			
			while(rs.next()) {
				list.add(
						new Member(
								rs.getInt(1),
								rs.getString(2),
								rs.getString(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getTimestamp(7,Calendar.getInstance()))
								);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(rs);
			DbClose.close(stmt);
		}
		return list;
	}
	
	//회원 정보 수정 - update
	public int updateMember(Connection conn,Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlUpdate = "update member set memberPw = ?, account = ? where memberId = ?";
		try {
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setString(1,member.getMemberPw());
			pstmt.setString(2,member.getAccount());
			pstmt.setString(3,member.getMemberId());
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		
		return result;
	}
	
	//회원 id 일치 확인 메소드
	public int idCheck(Connection conn,String id) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		String sqlCheck = "select * from member where memberId= ?";
		try {
			pstmt = conn.prepareStatement(sqlCheck);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(rs);
			DbClose.close(pstmt);
		}
		
		return result;
	}
	
	//회원 삭제
	public int deleteMember(Connection conn,String id,String pw) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlDel = "delete from member where memberId=? and memberPw=?";
		try {
			pstmt = conn.prepareStatement(sqlDel);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
//	public static Timestamp changeDate(ResultSet rs) {
//		
//		Timestamp ts = null;
//		
//		return ts;
//	}
}

