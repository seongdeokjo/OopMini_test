package manager;

public class Manager {
	//manager의 인스턴스 생성 
	private int managercode;
	private String mid;
	private String mpw;
	
	//생성자
	public Manager(int managercode, String mid, String mpw) {
		this.managercode = managercode;
		this.mid = mid;
		this.mpw = mpw;
	}

	public int getManagercode() {
		return managercode;
	}
	public void setManagercode(int managercode) {
		this.managercode = managercode;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpw() {
		return mpw;
	}
	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	
	@Override
	public String toString() {
		return "Manager [managercode=" + managercode + ", mid=" + mid + ", mpw=" + mpw + "]";
	}
}
