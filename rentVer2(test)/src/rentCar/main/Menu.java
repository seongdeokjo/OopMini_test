package rentCar.main;

import rentCar.dao.CarDao;
import rentCar.dao.MemberDao;
import rentCar.dao.RentDao;
import rentCar.handler.CarHandler;
import rentCar.handler.MemberHandler;
import rentCar.handler.RentHandler;


public class Menu {

	public static void main(String[] args) {
		MemberHandler mbH = new MemberHandler(MemberDao.getInstance());
		CarHandler ch = new CarHandler(CarDao.getInstance());
		RentHandler rh = new RentHandler(RentDao.getInstance());
		//rent
//		rh.rentList();
//		rh.rentCar();
		
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
