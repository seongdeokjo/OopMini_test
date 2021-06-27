package main;
//2021.06.25

//문제점 : 1.로그인 확인후 회원의 렌트 현황에서 새로운 메소드를 추가해야하지 하는지? -> 회원의 id or 이름을 입력 받아야하는 번거로움 
//2. 

import java.util.Scanner;

import manager.*;

public class UI {
	MemberManage mm = new MemberManage(ManagerDao.getInstance());
	CarManage cm = new CarManage(ManagerDao.getInstance());

	// 전체 메뉴
	public void start() {
		System.out.println("1.회원 2.비회원 3.관리자 4.종료");
		while (true) {
			int num = 0;

			num = Integer.parseInt(getUserInput());
			switch (num) {
			// 1번은 회원의 기능
			case 1:
				menuMember();
				break;
			// 2번은 비회원 회원가입
			case 2:
				menuNoneMM();
				break;
			// 3번은 관리자 기능
			case 3:
				menuManager();
				break;
			// 4번은 db접속 해제후 프로그램 강제 종료
			case 4:
				mm.endDb();
				System.exit(0);
			}
		}
	}

	public void menuMember() {
		// 회원은 로그인을 먼저 진행해주세요.
		// 1.대여 2.반납 3.대여 내역 4.회원정보수정 5. 로그아웃
		// 2021 06 23
		// 대여,반납 -> 25일 완료
		// 2021 06 24
		// 회원정보 수정, 결제기능 추가 x ->
		int num = 0;
		System.out.println("회원 로그인을 진행합니다.");
		// 로그인
		if (mm.memberLogin() == true) {
			while (true) {

				System.out.println("메뉴 선택을 해주세요.");
				System.out.println("1.대여 2.반납 3.현재대여정보 4.전체차량정보 5.결제 정보 6.로그아웃");
				num = Integer.parseInt(getUserInput());
				switch (num) {

				case 1:
					// 대여 메서드
					cm.rentCar2();
					break;
				case 2:
					// 반납 메서드
					cm.returnCar();
					break;
				case 3:
					// 현재 이용 정보
					mm.currInfo();
					break;
				case 4:
					// 차량 이용 현황 메서드
					System.out.println("전체 차량 정보를 나타냅니다.");
					cm.carList();
					break;
				case 5:
					// 결제 정보
					break;
				case 6:
					if (mm.Logout() == true) {
						start();
					} else {
						break;
					}
				}
			}
		} else {
			menuMember();
		}
	}

	// 2번 비회원 선택시
	public void menuNoneMM() {
		System.out.println("회원가입을 시작합니다.");
		mm.addMember();
		System.out.println("회원가입이 완료되었습니다.");
		start();
	}

	// 3번 관리자 선택시
	// 1. 관리자 로그인 2. 회원 리스트 3.회원 정보 삭제 4.차량 등록 5.차량 삭제 6. 관리자 로그아웃
	public void menuManager() {
		System.out.println("관리자모드로 접속합니다.");

		int num = 0;
//		if (mm.managerLogin() == true) {
			if (mm.managerLogin() == true) {
			while (true) {

				System.out.println("메뉴를 선택해주세요.");
				System.out.println("1.회원 리스트 2.회원 정보 삭제 3.차량 등록 4.차량 삭제 5.관리자 로그아웃");
				num = Integer.parseInt(getUserInput());
				switch (num) {
				// 회원 리스트
				case 1:
					mm.memberList();
					break;
				// 회원 삭제
				case 2:
					mm.deletMember();
					break;
				// 차량 등록
				case 3:
					cm.addCar();
					break;
				// 차량 삭제
				case 4:
					cm.removeCar();
					break;
				// 관리자 로그아웃
				case 5:
					// 로그인 상태일 경우
					if (mm.Logout() == true) {
						start();
					} else {
						break;
					}
				default:
					System.out.println("올바른 숫자를 입력하세요.");
					break;
				}
			}
		} else {
			menuManager();
		}
	}

	public static String getUserInput() {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		return input;
	}
}