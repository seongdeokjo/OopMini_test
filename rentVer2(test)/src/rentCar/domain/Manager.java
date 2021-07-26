package rentCar.domain;

public class Manager {
	private int idx;
	private String managerId;
	private String managerPw;
	
	public Manager(int idx, String managerId, String managerPw) {
		this.idx = idx;
		this.managerId = managerId;
		this.managerPw = managerPw;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerPw() {
		return managerPw;
	}
	public void setManagerPw(String managerPw) {
		this.managerPw = managerPw;
	}
	
}
