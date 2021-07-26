package rentCar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import rentCar.domain.Car;
import rentCar.domain.Rent;
import util.DbClose;

public class RentDao {
	private RentDao() {}
	private static RentDao rDao;
	public static RentDao getInstance() {
		if(rDao == null) {
			rDao = new RentDao();
		}
		return rDao;
	}
	
	//렌트 내역 전체조회 -> 관리자 view
	public ArrayList<Rent> getRentList(Connection conn){
		ArrayList<Rent> list = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String sqlSel = "select * from rent order by idx";
			rs = stmt.executeQuery(sqlSel);
			
			list = new ArrayList<Rent>();
			
			while(rs.next()) {
				list.add(
						new Rent(
								rs.getInt(1),
								rs.getInt(2),
								rs.getInt(3),
								rs.getTimestamp(4, Calendar.getInstance()),
								rs.getTimestamp(5, Calendar.getInstance()),
								rs.getInt(6),
								rs.getInt(7)
								));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(rs);
			DbClose.close(stmt);
		}
		return list;
	}
	//렌트 내역 저장
	public int insertRent(Connection conn,int period,String id,int carNum) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlInsert = "insert into rent (totalPrice,rentperiod,returnDate,memberCode,carCode) "
				+ "values(? * (select carPrice from car where carNumber =?),"
				+ "?,date_add(now(),interval ? day),(select idx from member where memberId =?),"
				+ "(select idx from car where carNumber = ?))";
		try {
			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setInt(1, period);
			pstmt.setInt(2,carNum);
			pstmt.setInt(3, period);
			pstmt.setInt(4, period);
			pstmt.setString(5, id);
			pstmt.setInt(6, carNum);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}	
		return result;
	}
	//대여 성공시 차량 대여 불가 표시
	public int usingCar(Connection conn,int carNum) {
		PreparedStatement pstmt = null;
		int result = 0;
		boolean ck = true;
		String sqlUpdate = "update car set useCar = ? where carNumber =?";
		try {
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setBoolean(1, ck);
			pstmt.setInt(2, carNum);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		
		return result;
		
	}
	//대여 가능 차량
	public ArrayList<Car> availableCar(Connection conn) {
		ArrayList<Car> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sqlSel = "select carName,carNumber,carPrice from car where useCar =?";
		try {
			pstmt = conn.prepareStatement(sqlSel);
			pstmt.setBoolean(1, false);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<Car>();
			
			while(rs.next()) {
				list.add(new Car(
						rs.getString(1),
						rs.getInt(2),
						rs.getInt(3)
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		return list;
	}
	
	
	
	
	
	//반납 -> delete? 아니면 rent 칼럼 추가-> 반납완료(true) 표시
	//반납하고 다시 차량 이용가능여부 표시 update-> false
	//반납 차량 다시 대여가능한 차량으로 변경해주는 메소드
	public int canUseCar(Connection conn, int num) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sqlUpdate = "update car c join rent r on c.idx = r.carCode set useCar = 0 where carNumber = ?";
		try {
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
