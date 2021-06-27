package manager;
import java.util.List;
import java.util.Scanner;

import car.*;

public class CarManage {

	private ManagerDao dao;
	private Scanner scan;
	public CarManage(ManagerDao dao) {
		this.dao = dao;
		scan = new Scanner(System.in);
	}
	//2021.06.24 
	
	//rent 테이블을 이용 
	//회원의 기본 정보 수정 
	
	// 차량 정보 리스트 출력
	public void carList() {
			List<Car> list = dao.getCarList();

			System.out.println("자동차 정보 리스트");
			System.out.println("------------------------------------------------------------------");
			System.out.println("차번호 \t 차이름 \t 차크기 \t 탑승가능인원 \t 연식 \t 연료 \t");
			System.out.println("------------------------------------------------------------------");

			for (Car car : list) {
				System.out.printf("%s \t %s \t %s \t %d \t %d \t %s \n", 
						car.getCarnumber(), 
						car.getCarname(),
						car.getCarsize(),
						car.getCarseat(), 
						car.getCaryear(), 
						car.getFuel()
						);
			}
			System.out.println("-------------------------------------------------------------------");
	}
	// 차량 등록
	public void addCar() {

			System.out.println("새로운 차량을 등록합니다.");
			System.out.println("차번호 차이름 차크기 탑승인원 연식 연료  형식으로 입력해주세요.");
			System.out.println("예시) 123456 sonata middle 5 2020 휘발유 ");
			String inputData = scan.nextLine();
			String[] carData = inputData.split(" ");
			// car 생성자 : car 테이블
			Car car = new Car(
					0, 
					carData[0], 
					carData[1], 
					carData[2], 
					Integer.parseInt(carData[3]),
					Integer.parseInt(carData[4]),
					carData[5],
					0);
							
			int result = dao.insertCar(car);

			if (result > 0) {
				System.out.println("새로운 차량이 등록되었습니다.");
			} else {
				System.out.println("등록이 실패되었습니다.");
			}
	}

	// 차량 삭제
	public void removeCar() {
			
			System.out.println("차량의 삭제를 시작합니다.");
			carList();
			System.out.println("차량 번호로 입력하세요.");
			int carreg = Integer.parseInt(scan.nextLine());

			int result = dao.deleteCar( carreg);

			if (result > 0) {
				System.out.println("차량이 삭제되었습니다.");
			} else {
				System.out.println("차량의 정보가 없습니다.");
			}
	}
	
	//차량 대여
	public void rentCar() {		
			System.out.println("대여할 차량번호 입력");
			String carnumber = scan.nextLine();
			
			int result = dao.checkRentCar(carnumber);
			
			if(result > 0)	{
				System.out.println("대여를 시작합니다.");				
			}else {
				System.out.println("수정 실패");
			}
	}
	// 자동차 대여
	public	void rentCar2() {
			while(true) {
			System.out.println("메뉴로 돌아기는 0번을 눌러주세요./ 계속하려면 enter키");
			String out = scan.nextLine();
		if(out.equals("0")) {
				break;
		}else {
			System.out.println("대여가능한 차량의 목록을 나타냅니다.");
			availableList();
			System.out.println("챠랑의 종류를 입력하세요");
			String carsize = scan.nextLine();
			System.out.println("대여할 기간을 입력하세요.");
			String period = scan.nextLine();
			System.out.println("자동차 번호를 입력하세요.");
			String carnumber = scan.nextLine();
			System.out.println("이용자의 운전면허 번호를 입력하세요.");
			String carreg = scan.nextLine();
	
			//2021 06.24
		//예외 처리 추가 1이외에 다른 데이터가 들어올시 예외처리 
		//문제점 : 차번호 입력시 db에 없는 값이 들어와도 대여완료가 출력		
			int result = dao.addRentCar(period,carsize,carnumber,carreg);
						
				if(result > 0) {
					System.out.println("대여가 완료 되었습니다.");
					rentCar();
					break;
				}else {
					System.out.println("번호를 다시 입력하세요.");
				} 
				}
			}
	}

	// 자동차 반납
	public	void returnCar() {
			
			System.out.println("반납할 차량번호 입력");
			String carnumber = scan.nextLine();
					
				int result = dao.checkReturnCar(carnumber);
		
				if(result > 0) {
					System.out.println("반납이 완료되었습니다.");
				} else  {
					System.out.println("반납실패!!");
				}				
	}
	//렌트 현황 삭제
	public void returnCar2() {
	
		while(true) {
		System.out.println("뒤로가기 하시려면 0번을 눌러주세요./ 계속 진행은 enter키");
		String out = scan.nextLine();
		if(out.equals("0")) {
			break;
		}else {		
		rentList();
		System.out.println("회원아이디를 입력하세요");
		String id = scan.nextLine();
		int result = dao.deleteRentInfo(id);
		
		if(result > 0) {
			returnCar() ;
		}else {
			System.out.println("반납 실패");
			}
		}
	}		
}
	// 렌트 중인 차량 목록		
	public	void rentList() {
			
			List<Car> Clist = dao.rentList();
			
			System.out.println("렌트 중인 차량 목록");
			System.out.println("---------------------------------------------------");
			System.out.println("코드번호 \t차번호 \t차이름 \t차종류 \t탑승인원 \t연식 \t연료");
			System.out.println("---------------------------------------------------");
			
			for(Car car : Clist) {
				System.out.printf("%d\t%s\t%s\t%s\t%d\t%d\t%s\n",
				car.getCarcode(),
				car.getCarnumber(),
				car.getCarname(),
				car.getCarsize(),
				car.getCarseat(),
				car.getCaryear(),
				car.getFuel());
			}
			
			System.out.println("----------------------------------------------");
	}
	
	// 이용 가능 차량 목록
	public	void availableList() {
			
			List<Car> Clist = dao.availableList();
			
			System.out.println("대여 가능 차량 목록");
			System.out.println("--------------------------------------------------");
			System.out.println("코드번호 \t차번호 \t차이름 \t차종류 \t탑승인원 \t연식 \t연료");
			System.out.println("--------------------------------------------------");
						
			for(Car car : Clist) {
				System.out.printf("%d \t%s \t%s \t%s \t%d \t%d \t%s \n",
				car.getCarcode(),
				car.getCarnumber(),
				car.getCarname(),
				car.getCarsize(),
				car.getCarseat(),
				car.getCaryear(),
				car.getFuel(),
				car.getRentck()
				);
			}			
			System.out.println("--------------------------------------------------");	
	}
}