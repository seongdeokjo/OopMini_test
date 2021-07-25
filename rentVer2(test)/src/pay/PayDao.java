package pay;

public class PayDao {
	private PayDao() {}
	private static PayDao pDao;
	public static PayDao getInstance() {
		if(pDao == null) {
			pDao = new PayDao();
		}
		return pDao;
	}
	//결제 
}
