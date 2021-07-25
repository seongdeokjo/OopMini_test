package member;

import java.sql.Connection;
import java.util.List;

import util.DbConn;
import util.ScannerUtil;

public class MemberHandler {
	private MemberDao dao;
	private Connection conn;

	public MemberHandler(MemberDao dao) {
		this.dao = dao;
	}
	//회원 전체 리스트
	public void memberList() {
		conn = DbConn.getConnecting();
		List<Member> list = dao.getMemberList(conn);
		System.out.println("회원의 리스트를 출력합니다.");

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");
		System.out.println("회원번호 \t\t id \t\t 비밀번호 \t\t 이름 \t\t 면허 \t\t\t계좌 \t\t 가입일");
		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------");

		for (Member member : list) {
			System.out.printf("%d\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\n", member.getIdx(), member.getMemberId(),
					member.getMemberPw(), member.getMemberName(), member.getRegNumber(), member.getAccount(),
					member.getRegDate());
		}
	}
	//회원 가입 
	public void joinMember() {
		conn = DbConn.getConnecting();
		System.out.println("회원 가입을 시작합니다.");
		System.out.println("id를 입력하세요");
		String id = ScannerUtil.getInputString();
		System.out.println("비밀번호를 입력하세요.");
		String pw = ScannerUtil.getInputString();
		System.out.println("이름을 입력하세요.");
		String name = ScannerUtil.getInputString();
		System.out.println("면허번호를 입력하세요.");
		String regNum = ScannerUtil.getInputString();
		System.out.println("계좌번호를 입력하세요.");
		String account = ScannerUtil.getInputString();
		
		Member member = new Member(id,pw,name,regNum,account);
		
		int result = dao.insertMember(conn, member);
		
		if(result >0) {
			System.out.println("회원가입이 완료되었습니다.");
		}else {
			System.out.println("입력 실패!!!");
		}
	}
	//회원 정보 수정
	public void editMember() {
		conn = DbConn.getConnecting();
		System.out.println("아이디를 입력하세요.");
		String id = ScannerUtil.getInputString();
		if(checkId(id) == true) {
			System.out.println("변경할 비밀번호를 입력하세요.");
			String pw = ScannerUtil.getInputString();
			System.out.println("변경할 계좌를 입력하세요.");
			String account = ScannerUtil.getInputString();
			
			Member member = new Member(id,pw,account);
			
			int result = dao.updateMember(conn, member);
			
			if(result > 0) {
				System.out.println("수정 완료");
			}else {
				System.out.println("수정 실패!!");
			}
		}else {
			System.out.println("저장된 정보가 없습니다.");
		}
	}
	
	//회원 id 일치여부 판별
	public boolean checkId(String id) {
		conn = DbConn.getConnecting();
		boolean ck = false;
		int result = dao.idCheck(conn, id);
		if(result > 0) {
			ck = true;
		}
		return ck;
	}
	
	//회원 삭제
	public void removeMember() {
		conn = DbConn.getConnecting();
		System.out.println("회원 아이디를 입력하세요.");
		String id = ScannerUtil.getInputString();
		System.out.println("비밀번호를 입력하세요.");
		String pw = ScannerUtil.getInputString();
		
		System.out.println("정말 삭제하시겠습니까?");
		String answer = ScannerUtil.getInputString();
		if(answer.equals("yes")) {
			int result = dao.deleteMember(conn, id, pw);
			
			if(result > 0) {
				System.out.println("삭제 완료!");
			}else {
				System.out.println("삭제 실패");
			}
		}
	}
}