package manager;

import java.sql.Connection;

public class ManagerHandler {
	private ManagerDao dao;
	private Connection conn;
	
	public ManagerHandler(ManagerDao dao) {
		this.dao = dao;
	}
	
}
