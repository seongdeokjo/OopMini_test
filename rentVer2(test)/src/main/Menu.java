package main;

import car.*;
import member.*;
import rent.*;


public class Menu {

	public static void main(String[] args) {
		MemberHandler mbH = new MemberHandler(MemberDao.getInstance());
		CarHandler ch = new CarHandler(CarDao.getInstance());
		RentHandler rh = new RentHandler(RentDao.getInstance());
		//rent
		rh.rentList();
		rh.rentCar();
		
		//car
//		ch.listCar();
//		ch.addCar();
//		ch.editCar();
//		ch.removeCar();
//		ch.listCar();
		
		//member
//		mbH.memberList();
//		mbH.joinMember();
//		mbH.editMember();
//		mbH.removeMember();
//		mbH.memberList();
	}
}
