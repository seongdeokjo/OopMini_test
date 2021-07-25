package member;

import java.sql.Timestamp;

public class Member {

	private int idx;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String regNumber;
	private String account;
	private Timestamp regDat;
		
	public Member(int idx, String memberId, String memberPw, String memberName, String regNumber, String account,
			Timestamp regDat) {
		this.idx = idx;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.regNumber = regNumber;
		this.account = account;
		this.regDat = regDat;
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

	public Timestamp getRegDat() {
		return regDat;
	}

	public void setRegDat(Timestamp regDat) {
		this.regDat = regDat;
	}

}