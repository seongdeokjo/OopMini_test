package manager;

import java.util.Scanner;

import member.*;

public class ManagerManage {
	MemberManage mbm = new MemberManage(MemberDao.getInstance());
	// 메소드
	// 회원 정보 관리
	// 25일 : 회원 정보 수정
	private ManagerDao dao;
	private Scanner scan;
	static boolean ck = false;

	public ManagerManage(ManagerDao dao) {
		this.dao = dao;
		scan = new Scanner(System.in);
	}

	public void endDb() {
		System.out.println("db 접속을 종료하고 프로그램을 종료합니다.");
		dao.close();
	}



	// manager 로그인 메서드
	public boolean managerLogin() {
		System.out.println("아이디를 입력하세요.");
		String id = scan.nextLine();
		System.out.println("비밀번호를 입력하세요.");
		String pw = scan.nextLine();
		if (dao.managerLogin(id, pw) > 0) {
			ck = true;
		} else {
			ck = false;
		}
		return ck;
	}

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

//2021.06.25 각각의 예외 사항 : 회원가입시 발생할 수 있는 예외 처리 -> db 제약조건에 따라 자바에서도 처리 해줄 수 있게
//정보추가 : 생년월일, 운전경력 등 

	// 회원 정보 삭제 메소드
	public void deletMember() {
		System.out.println("회원의 정보를 삭제합니다.");
		mbm.memberList();
		System.out.println("삭제를 원하시는 멤버의 코드번호를 입력하세요.");
		int membercode = Integer.parseInt(scan.nextLine());

		int result = dao.deleteMember(membercode);

		if (result > 0) {
			System.out.println("멤버가 삭제되었습니다.");
		} else {
			System.out.println("해당 멤버의 정보가 없습니다.");
		}
	}

	
}