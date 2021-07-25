package rent;

import java.sql.Timestamp;

public class Rent {
	private int idx;
	private int totalPrice;
	private int rentPeriod;
	private Timestamp returnDate;
	private Timestamp rentDate;
	private int memberCode;
	private int carCode;
	


	public Rent(int idx, int totalPrice, int rentPeriod, Timestamp returnDate, Timestamp rentDate, int memberCode,
			int carCode) {
		this.idx = idx;
		this.totalPrice = totalPrice;
		this.rentPeriod = rentPeriod;
		this.returnDate = returnDate;
		this.rentDate = rentDate;
		this.memberCode = memberCode;
		this.carCode = carCode;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getRentPeriod() {
		return rentPeriod;
	}

	public void setRentPeriod(int rentPeriod) {
		this.rentPeriod = rentPeriod;
	}

	public Timestamp getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Timestamp returnDate) {
		this.returnDate = returnDate;
	}

	public Timestamp getRentDate() {
		return rentDate;
	}

	public void setRentDate(Timestamp rentDate) {
		this.rentDate = rentDate;
	}

	public int getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(int memberCode) {
		this.memberCode = memberCode;
	}

	public int getCarCode() {
		return carCode;
	}

	public void setCarCode(int carCode) {
		this.carCode = carCode;
	}

}
