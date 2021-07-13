package member;

import java.util.List;
import java.util.Scanner;

import manager.*;
import car.*;

public class MemberManage {
	// 메소드
	// 회원 정보 관리
	// 29일 : 회원 정보 수정(edit), 해당 고객의 본인의 정보 삭제,
	private MemberDao dao;
	CarManage cm;
	private Scanner scan;
	static boolean ck = false;

	public MemberManage(MemberDao dao) {
		this.dao = dao;
		scan = new Scanner(System.in);
	}

	// 회원 가입 메서드
	public void addMember() {
		while (true) {
			System.out.println("회원가입을 시작합니다.");
			System.out.println("아이디를 입력해주세요.");
			String id = scan.nextLine();
			if (dao.checkMemberId(id) > 0) {
				System.out.println("id가 중복되었습니다. 다시 입력하세요.");
				continue;
			} else if (id.trim() == "") {
				System.out.println("id는 공백으로 입력될 수 없습니다. 다시 입력해주세요.");
				continue;
			} else {
				System.out.println("사용가능한 id입니다.");
				System.out.println("비밀번호 이름 운전면허 이메일 주소 계좌번호 순으로 입력해주세요.");
				System.out.println("예) 1234 홍길동 111111 t@naver.com seoul ####-####");

				String inputData = scan.nextLine();
				String[] memberdata = inputData.split(" ");

				Member member = new Member(0, id, memberdata[0], memberdata[1], memberdata[2], memberdata[3],
						memberdata[4], memberdata[5], 0);

				int result = dao.insertMember(member);

				if (result > 0) {
					System.out.println("회원가입이 완료되었습니다.");
					break;
				} else {
					System.out.println("입력 실패!!!!");
					continue;
				}
			}
		}
	}

	// 회원 리스트 출력 메소드
	// Dao에서 데이터 리스트를 받고 출력 처리
	public void memberList() {

		List<Member> list = dao.getMemberList();
		System.out.println("회원의 리스트를 출력합니다.");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println("멤버번호 \t\t id \t\t 이름 \t\t 면허번호 \t\t email \t\t\t주소 \t\t 계좌번호 \t\t잔액");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

		for (Member member : list) {
			System.out.printf("%d\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%d\n", member.getMembercode(), member.getId(),
					member.getName(), member.getCarreg(), member.getEmail(), member.getAddress(), member.getAccount(),
					member.getBalance());
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
	}

	// member 로그인 메서드
	public boolean memberLogin() {
		System.out.println("아이디를 입력하세요.");
		String id = scan.nextLine();
		System.out.println("비밀번호를 입력하세요.");
		String pw = scan.nextLine();
		if (dao.memberLogin(id, pw) > 0) {
			ck = true;
		} else {
			ck = false;
		}
		return ck;
	}

	// member 로그아웃 기능
	public boolean Logout() {
		System.out.println("로그아웃 하시려면 yes / 돌아가시려면 no를 입력하세요.");
		String answer = scan.nextLine();
		switch (answer) {
		case "yes":
			ck = false;
			System.out.println("로그아웃 되었습니다.");
			break;
		case "no":
			ck = true;
			break;
		default:
			System.out.println("yes or no만 입력하세요");
			ck = true;
			break;
		}
		return ck;
	}
	// 2021.07-13 추가
	// member의 account 정보 찾기
	//id를 입력받아 계좌 및 잔액 찾는 메소드
	public void accountId() {
		System.out.println("이용자의 id를 입력해주세요.");
		String id = scan.nextLine();
		List<Member> list = dao.getAccount(id);
		System.out.println("회원의 리스트를 출력합니다.");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println("id \t\t 이름 \t\t 계좌번호 \t\t 잔액");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

		for (Member member : list) {
			System.out.printf("%s\t\t%s\t\t%s\t\t%d\n", 
					member.getId(), member.getName(),member.getAccount(), member.getBalance());
		}
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

	}
	//member 대여비 입금 메소드
	public void insertDeposit() {
		System.out.println("계좌 번호를 입력해주세요."); 
		String account = scan.nextLine();
		System.out.println("입금할 금액을 입력해주새요.");
		int balance = scan.nextInt();
		if(dao.deposit(account, balance)>0) {
			System.out.println("입금이 완료되었습니다.");
		}else {
			System.out.println("입금실패!!");
		}
	
	}

	// 사용자의 현재 대여 정보 출력
	public void currInfo() {
		System.out.println("이용자의 렌트 현황을 출력합니다.");
		System.out.println("이용자의 id를 입력해주세요.");
		String id = scan.nextLine();

		if (id != null) {
			System.out.println(id + "의 대여 정보 입니다.");
			dao.currRentInfo(id);
		} else {
			System.out.println("id를 다시 입력하여주세요.");
		}
	}
}