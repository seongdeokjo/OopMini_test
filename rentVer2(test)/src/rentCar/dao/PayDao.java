package rentCar.dao;

public class PayDao {
	private PayDao() {}
	private static PayDao pDao;
	public static PayDao getInstance() {
		if(pDao == null) {
			pDao = new PayDao();
		}
		return pDao;
	}
	//결제 insert
	//전체 결재 내역 출력 select
	//결제 취소 -> delete
	//결제전 금액확인 기간,차량번호를 매게변수로 받아서 rent 데이터 생성 전에 보여주기
}
