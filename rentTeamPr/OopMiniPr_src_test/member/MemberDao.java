package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.*;

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
	
	//로그인 
	public int memberLogin(Connection conn,String id, String Pw) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select pw from member where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).contentEquals(Pw)) {
					System.out.println(id + "님 로그인 되었습니다.");

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
		}finally {
			DbClose.close(rs);
			DbClose.close(pstmt);
		}
		System.out.println("오류");
		return -2; // db 오류
	}

	// 멤버 정보 입력
	int insertMember(Connection conn,Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String sql = "insert into member values(MEMBER_membercode_SEQ.nextval, ?, ?, ?, ?, ?, ?,?,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getCarreg());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getAccount());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(pstmt);
		}
		return result;
	}

	// 21.07.07 id 중복체크 메서드 기능
	// 멤버의 중복 id 체크
	public int checkMemberId(Connection conn,String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from member where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(rs);
			DbClose.close(pstmt);
		}
		return result;
	}

	// 멤버의 정보 조회
	ArrayList<Member> getMemberList(Connection conn) {
		ArrayList<Member> list = null;

		// 데이터 베이스의 member 테이블 이용 select 결과를 ->list 에 저장
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			String sql = "select * from member order by membercode ";

			// 결과 받아오기
			rs = stmt.executeQuery(sql);

			list = new ArrayList<>();

			// 데이터를 member 객체로 생성 -> list에 저장
			// ()안에 member table의 데이터 작성해야함

			while (rs.next()) {
				list.add(new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getInt(9)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(rs);
			DbClose.close(stmt);
		}
		return list;
	}
	//2021-07-13 생성
	// 결제를 위해 회원의 id를 찾는 메소드
	public ArrayList<Member> getAccount(Connection conn,String id) {
		ArrayList<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select id,name,account,balance from member where id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Member(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(rs);
			DbClose.close(pstmt);
		}

		return list;
	}
	// 2021-07-13 생성
	// member id -> 계좌 정보를 출력 -> 계좌 번호,금액을 입력받아 member테이블 update
	public int deposit(Connection conn,String account, int balance) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "update member set balance = balance + ? where account = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, balance);
			pstmt.setString(2, account);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(rs);
			DbClose.close(pstmt);
		}

		return result;
	}

	// 현재 대여 중인 고객의 렌트 정보 출력
	public void currRentInfo(Connection conn,String id) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select r.rentcode, c.carnumber, c.carname, c.carsize, m.id, m.name, m.carreg, r.pay,r.rentperiod,r.rent_date from car c, member m, rent r where c.carcode = r.carcode and m.membercode = r.membercode and m.id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println(
						"----------------------------------------------------------------------------------------------");
				System.out.println("렌트 번호\t 차량 번호\t 차 이름\t 차량 종류\t 고객id\t고객 이름\t면허번호\t결제 금액\t 기간\t 반납 날짜");
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7)
						+ "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getString(10));
				System.out.println(
						"----------------------------------------------------------------------------------------------");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbClose.close(rs);
			DbClose.close(pstmt);
		}
	}
}
