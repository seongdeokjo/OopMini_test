package rentCar.handler;

import java.sql.Connection;

import rentCar.dao.ManagerDao;

public class ManagerHandler {
	private ManagerDao dao;
	private Connection conn;
	
	public ManagerHandler(ManagerDao dao) {
		this.dao = dao;
	}
	
}
