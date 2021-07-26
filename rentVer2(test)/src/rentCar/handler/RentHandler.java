package rentCar.handler;

import java.sql.Connection;
import java.util.List;

import rentCar.dao.RentDao;
import rentCar.domain.Car;
import rentCar.domain.Rent;
import util.DbConn;
import util.ScannerUtil;

public class RentHandler {
	private RentDao rDao;
	private Connection conn;
	public RentHandler(RentDao rDao) {
		this.rDao = rDao;
	}
	//rent 리스트 출력
	public void rentList() {
		conn = DbConn.getConnecting();
		List<Rent> list = rDao.getRentList(conn);
		System.out.println("렌트 내역을 출력합니다.");
		
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("렌트번호\t\t총 가격\t\t대여기간\t\t반납일\t\t\t\t대여일\t\t\t\t회원코드\t\t차량코드");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------");

		for (Rent rent : list) {
			System.out.printf("%d\t\t%d\t\t%d\t\t%s\t\t%s\t\t%d\t\t%d\n",
						rent.getIdx(),rent.getTotalPrice(),rent.getRentPeriod(),rent.getReturnDate(),rent.getRentDate(),rent.getMemberCode(),rent.getCarCode());
		}
	}
	//대여 가능 차량의 목록
	public void availableList() {
		
		conn = DbConn.getConnecting();
		
		List<Car> list = rDao.availableCar(conn);
		if(!list.isEmpty()) {
		
		System.out.println("대여 가능한 차량의 목록입니다.");
		
		System.out.println(
				"---------------------------------------------------------------------");
		System.out.println("차량 이름\t\t차량 번호\t\t가격");
		System.out.println(
				"---------------------------------------------------------------------");

		for (Car car : list) {
			System.out.printf("%s\t\t%d\t\t%d\n",
				car.getCarName(),car.getCarNumber(),car.getCarPrice());
		}
		}else {
			System.out.println("현재 대여 가능한 차량이 없습니다.");
		
		}
	}
 	
	//렌트 내역 생성
	public void rentCar() {
		conn = DbConn.getConnecting();
		System.out.println("렌트를 시작합니다.");
		availableList();
		System.out.println("대여 기간을 입력하세요.");
		int period = ScannerUtil.getInputInteger();
		System.out.println("회원 아이디를 입력하세요.");
		String id = ScannerUtil.getInputString();
		System.out.println("차량 번호를 입력하세요.");
		int num = ScannerUtil.getInputInteger();
		int result = rDao.insertRent(conn, period, id, num);
		
		if(result > 0) {
			System.out.println("대여 성공");
			rDao.usingCar(conn, num);
		}else {
			System.out.println("대여 실패");
		}
	}
	
}
