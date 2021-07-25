package main;

import car.CarDao;
import car.CarHandler;
import member.MemberDao;
import member.MemberHandler;

public class Menu {

	public static void main(String[] args) {
		MemberHandler mbH = new MemberHandler(MemberDao.getInstance());
		CarHandler ch = new CarHandler(CarDao.getInstance());
		
		//car
		ch.listCar();
//		ch.addCar();
//		ch.editCar();
//		ch.removeCar();
		ch.listCar();
		
		//member
//		mbH.memberList();
//		mbH.joinMember();
//		mbH.editMember();
//		mbH.removeMember();
//		mbH.memberList();
	}
}
