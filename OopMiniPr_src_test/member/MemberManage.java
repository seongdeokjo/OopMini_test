package member;

import java.util.List;
import java.util.Scanner;

import manager.*;
import car.*;
public class MemberManage {
	// 메소드
	// 회원 정보 관리
	//29일 : 회원 정보 수정(edit), 해당 고객의  본인의 정보 삭제, 
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
		System.out.println("아이디 비밀번호 이름 운전면허 이메일 주소 형식으로 입력해주세요.");
		System.out.println("에시) hi 1234 홍길동 111111 t@naver.com seoul");
		String inputData = scan.nextLine();
		String[] memberdata = inputData.split(" ");

		Member member = new Member(0, memberdata[0], memberdata[1], memberdata[2], memberdata[3], memberdata[4],
				memberdata[5]);

		int result = dao.insertMember(member);

		if (result > 0) {
			System.out.println("회원가입이 완료되었습니다.");
		} else {
			System.out.println("입력 실패!!!!");
		}
	}

	// 회원 리스트 출력 메소드
	// Dao에서 데이터 리스트를 받고 출력 처리
	public void memberList() {

		List<Member> list = dao.getMemberList();
		System.out.println("회원의 리스트를 출력합니다.");
		System.out.println("---------------------------------------------------------------------");
		System.out.println("멤버번호 \t id \t 이름 \t 면허번호 \t email \t 주소");
		System.out.println("---------------------------------------------------------------------");

		for (Member member : list) {
			System.out.printf("%d \t %s \t %s \t %s \t %s \t %s  \n ", member.getMembercode(), member.getId(),
					member.getName(), member.getCarreg(), member.getEmail(), member.getAddress());
		}
		System.out.println("---------------------------------------------------------------------");
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