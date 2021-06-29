package car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class CarDao {

	private static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String user = "hr";
	private static final String pw = "tiger";
	private Connection conn;


	private CarDao() {
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

	static private CarDao cDao;

	public static CarDao getInstance() {
		if (cDao == null) {
			cDao = new CarDao();
		}
		return cDao;
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

}
