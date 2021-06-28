package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import car.*;
import member.*;

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

	// 멤버 로그인
	public int memberLogin(String id, String Pw) {
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
		}
		System.out.println("오류");
		return -2; // db 오류
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

	// 멤버 정보 입력
	int insertMember(Member member) {
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String sql = "insert into member values(MEMBER_membercode_SEQ.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getCarreg());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());

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

	// 멤버의 정보 조회
	ArrayList<Member> getMemberList() {
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
				list.add(new Member(
						rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getString(4), 
						rs.getString(5),
						rs.getString(6), 
						rs.getString(7)
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
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

	// 전체 차량 정보 리스트
	ArrayList<Car> getCarList() {
		ArrayList<Car> list = null;

		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select carnumber, carname, carsize, carseat, caryear, fuel from car order by carnumber";

		try {
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			list = new ArrayList<>();

			while (rs.next()) {
				list.add(new Car(
						rs.getString(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getInt(4), 
						rs.getInt(5),
						rs.getString(6)
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	// 차량 등록
	int insertCar(Car car) {
		int result = 0;

		PreparedStatement pstmt = null;

		// sequence 생성-완료
		String sql = "insert into car values(car_carcode_SEQ.nextVal,?,?,?,?,?,?,0)";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, car.getCarnumber());
			pstmt.setString(2, car.getCarname());
			pstmt.setString(3, car.getCarsize());
			pstmt.setInt(4, car.getCarseat());
			pstmt.setInt(5, car.getCaryear());
			pstmt.setString(6, car.getFuel());

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

	// 차량 삭제
	int deleteCar(int carnumber) {

		int result = 0;

		PreparedStatement pstmt = null;

		String sql = "delete from car where carnumber = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, carnumber);

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

	// 자동차 대여
	// 자동차 테이블의 대여 이용 가능의 rentck 를 1로 변경
	int checkRentCar(String carnumber) {
		int result = 0;

		// 전달받은 Car객체의 데이터로 테이블에 저장 -> 결과값 반환
		PreparedStatement pstmt = null;
		try {
			String sql = "update car set rentck=1 where rentck !=1 and carnumber = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, carnumber);

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

	// rent 테이블에 새로운 데이터 입력
	int addRentCar(String period, String carsize, String carnumber, String carreg) {
		// 원래는 boolean 타입을 사용하여 차량번호만 받아 대여 상태를 표시하고 싶었지만
		// sql에서 boolean타입을 처리하는법과 대여 메소드를 만드는 법을 해결하지 못하여
		// 사용자에게 0 과1 을 입력 받음으로 자동차의 대여현황이 변화는 방법으로 선회하였습니다.
		int result = 0;

		// 전달받은 Car객체의 데이터로 테이블에 저장 -> 결과값 반환
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into rent values(rent_rentcode_seq.nextval,? * (select paymoney from pay where carsize = ?),?,sysdate+?,(select carcode from car where carnumber = ?),(select membercode from member where carreg = ?),1)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, period);
			pstmt.setString(2, carsize);
			pstmt.setString(3, period);
			pstmt.setString(4, period);
			pstmt.setString(5, carnumber);
			pstmt.setString(6, carreg);

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

	// 자동차 반납
	int checkReturnCar(String carnumber) {
		int result = 0;

		// 전달받은 Car객체의 데이터로 테이블에 저장 -> 결과값 반환
		PreparedStatement pstmt = null;
		try {
			String sql = "update car set rentck=0 where rentck !=0 and carnumber = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, carnumber);
			

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

	// 렌트 대여 정보 삭제
	int deleteRentInfo(String id) {

		int result = 0;

		// 전달받은 Car객체의 데이터로 테이블에 저장 -> 결과값 반환
		PreparedStatement pstmt = null;
		try {
			String sql = "delete from rent where membercode = (select membercode from member where id = ?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

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

	// 렌트 중인 차량 목록
	ArrayList<Car> rentList() {

		ArrayList<Car> Clist = null;

		// DB select결과를 Clist에저장
		Statement Cstmt = null;
		ResultSet Crs = null;

		try {
			Cstmt = conn.createStatement();

			String sql = "select * from Car where rentck = 1 order by carcode";

			// 결과받기
			Crs = Cstmt.executeQuery(sql);
			Clist = new ArrayList<>();
			// 데이터를 Car 객체로 생성 (list)
			while (Crs.next()) {
				Clist.add(new Car(
						Crs.getInt(1), 
						Crs.getString(2), 
						Crs.getString(3), 
						Crs.getString(4), 
						Crs.getInt(5),
						Crs.getInt(6), 
						Crs.getString(7), 
						Crs.getInt(8)
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (Crs != null) {
				try {
					Cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Clist;
	}

	// 렌트 이용가능한 메서드
	ArrayList<Car> availableList() {

		ArrayList<Car> Clist = null;

		// DB select결과를 Clist에저장
		Statement Cstmt = null;
		ResultSet Crs = null;

		try {
			Cstmt = conn.createStatement();

			String sql = "select * from Car where rentck = 0 order by carcode";

			// 결과받기
			Crs = Cstmt.executeQuery(sql);

			Clist = new ArrayList<>();
			// 데이터를 Car 객체로 생성 (list)
			while (Crs.next()) {
				Clist.add(new Car(
						Crs.getInt(1), 
						Crs.getString(2),
						Crs.getString(3), 
						Crs.getString(4), 
						Crs.getInt(5),
						Crs.getInt(6), 
						Crs.getString(7), 
						Crs.getInt(8)
						));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (Crs != null) {
				try {
					Cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return Clist;
	}

	// 현재 대여 중인 고객의 렌트 정보 출력
	public void currRentInfo(String id) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "select r.rentcode, c.carnumber, c.carname, c.carsize, m.id, m.name, m.carreg, r.pay,r.rentperiod,r.rent_date from car c, member m, rent r where c.carcode = r.carcode and m.membercode = r.membercode and m.id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("----------------------------------------------------------------------------------------------");
				System.out.println("렌트 번호\t 차량 번호\t 차 이름\t 차량 종류\t 고객id\t고객 이름\t면허번호\t결제 금액\t 기간\t 반납 날짜");
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"
						+ rs.getString(4) + "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7)
						+ "\t" + rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getString(10));
				System.out.println("----------------------------------------------------------------------------------------------");
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}