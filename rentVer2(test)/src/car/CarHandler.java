package car;

import java.sql.Connection;
import java.util.List;

import member.Member;
import util.DbClose;
import util.DbConn;
import util.ScannerUtil;

public class CarHandler {
	private CarDao cDao;
	private Connection conn;
	
	public CarHandler(CarDao cDao) {
		this.cDao = cDao;
	}
	//전체 차량 조회
	public void listCar() {
		conn = DbConn.getConnecting();
		List<Car> list = cDao.getCarList(conn);
		System.out.println("차량의 리스트를 출력합니다.");
		
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println("등록번호\t\t 차량이름\t\t차량번호\t\t차량가격\t\t대여중");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

		for (Car car : list) {
			System.out.printf("%d\t\t%s\t\t%d\t\t%d\t\t%b\n", 
					car.getIdx(), car.getCarName(),car.getCarNumber(),car.getCarPrice(),car.isUseCar());
		}
	}
	// 차량 등록
	public void addCar() {
		conn = DbConn.getConnecting();
		System.out.println("차량 등록을 시작합니다.");
		System.out.println("차량 이름을 입력하세요.");
		String name = ScannerUtil.getInputString();
		System.out.println("차량 번호를 입력하세요.");
		int num = ScannerUtil.getInputInteger();
		System.out.println("차량 가격을 입력하세요.");
		int price = ScannerUtil.getInputInteger();
		
		Car car = new Car(name,num,price);
		
		int result = cDao.insertCar(conn, car);
		
		if(result > 0) {
			System.out.println("차량 등록 성공!");
		}else {
			System.out.println("등록 실패!!");
		}
	}
	//차량 정보 수정
	public void editCar() {
		conn = DbConn.getConnecting();
		System.out.println("변경할 차량의 등록번호를 입력하세요.");
		int num = ScannerUtil.getInputInteger();
		System.out.println("변경할 금액을 입력하세요.");
		int price = ScannerUtil.getInputInteger();
		int result  = cDao.updateCar(conn,num,price);
		
		if(result > 0) {
			System.out.println("수정 완료!!");
		}else {
			System.out.println("수정 실패!!");
		}
	}
	//차량 삭제
	public void removeCar() {
		conn = DbConn.getConnecting();
		System.out.println("차량을 삭제합니다.");
		System.out.println("삭제할 차량의 등록번호를 입력하세요.");
		int num = ScannerUtil.getInputInteger();
		int result = cDao.deleteCar(conn, num);
		
		if(result > 0) {
			System.out.println("삭제 성공!");
		}else {
			System.out.println("삭제 실패!!");
		}
	}
}
