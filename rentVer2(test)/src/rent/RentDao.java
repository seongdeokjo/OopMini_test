package rent;


public class RentDao {
	private RentDao() {}
	private static RentDao rDao;
	public static RentDao getInstance() {
		if(rDao == null) {
			rDao = new RentDao();
		}
		return rDao;
	}
}
