package main;

import member.MemberDao;
import member.MemberHandler;

public class Menu {

	public static void main(String[] args) {
		MemberHandler mbH = new MemberHandler(MemberDao.getInstance());
		
		mbH.memberList();
//		mbH.joinMember();
//		mbH.editMember();
		mbH.removeMember();
		mbH.memberList();
	}
}
