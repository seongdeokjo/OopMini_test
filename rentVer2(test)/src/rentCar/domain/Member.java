package rentCar.domain;

import java.sql.Timestamp;

public class Member {

	private int idx;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String regNumber;
	private String account;
	private Timestamp regDate;
	
	
	
	public Member(String memberName) {
		this.memberName = memberName;
	}

	public Member(String memberId, String memberPw, String account) {
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.account = account;
	}

	//회원가입을 위한 생성자 
	public Member(String memberId, String memberPw, String memberName, String regNumber, String account) {
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.regNumber = regNumber;
		this.account = account;
	}

	public Member(int idx, String memberId, String memberPw, String memberName, String regNumber, String account,
			Timestamp regDate) {
		this.idx = idx;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.regNumber = regNumber;
		this.account = account;
		this.regDate = regDate;
	}
	

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDat(Timestamp regDate) {
		this.regDate = regDate;
	}

}