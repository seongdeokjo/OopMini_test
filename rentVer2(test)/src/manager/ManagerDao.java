package manager;

public class ManagerDao {

	// 1.외부 클래스 또는 인스턴스에서 해당 클래스로 인스턴스를 생성하지 못하도록 처리
	private ManagerDao() {}
	static private ManagerDao mnDao;

	// 3. 메소드를 통해서 반환 하도록 처리
	public static ManagerDao getInstance() {
		if (mnDao == null) {
			mnDao = new ManagerDao();
		}
		return mnDao;
	}
	
}
