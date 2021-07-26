package rentCar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import rentCar.domain.Car;
import util.DbClose;

public class CarDao {
	private CarDao() {}
	static private CarDao cDao;
	public static CarDao getInstance() {
		if(cDao == null) {
			cDao = new CarDao();
		}
		return cDao;
	}
	//차량 등록 -create
	public int insertCar(Connection conn,Car car) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlInsert = "insert into car (carName,carNumber,carPrice) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sqlInsert);
			pstmt.setString(1, car.getCarName());
			pstmt.setInt(2,car.getCarNumber());
			pstmt.setInt(3,car.getCarPrice());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		return result;
	}
	//차량 리스트 - read
	public ArrayList<Car> getCarList(Connection conn){
		ArrayList<Car> list = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			String sql = "select * from car order by idx";
			rs = stmt.executeQuery(sql);
			
			list = new ArrayList<Car>();
			
			while(rs.next()) {
				list.add(new Car(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getInt(4),
						rs.getBoolean(5)
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
	//차량 정보 변경 - update
	public int updateCar(Connection conn,int num,int price) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlUpdate = "update car set carPrice =? where idx =?";
		try {
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setInt(1, price);
			pstmt.setInt(2, num);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		
		return result;
	}
	
	//차량 삭제 - delete
	public int deleteCar(Connection conn,int num) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sqlDelete = "delete from car where idx =?";
		try {
			pstmt = conn.prepareStatement(sqlDelete);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DbClose.close(pstmt);
		}
		
		return result;
	}	
}
