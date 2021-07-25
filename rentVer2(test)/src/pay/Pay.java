package pay;

import java.sql.Timestamp;

public class Pay {
private int	idx;
private int	price;
private int	memberCode;
private Timestamp payDate;
private boolean	payCk;
private int	carCode;

public Pay(int idx, int price, int memberCode, Timestamp payDate, boolean payCk, int carCode) {
	this.idx = idx;
	this.price = price;
	this.memberCode = memberCode;
	this.payDate = payDate;
	this.payCk = payCk;
	this.carCode = carCode;
}

public int getIdx() {
	return idx;
}
public void setIdx(int idx) {
	this.idx = idx;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public int getMemberCode() {
	return memberCode;
}
public void setMemberCode(int memberCode) {
	this.memberCode = memberCode;
}
public Timestamp getPayDate() {
	return payDate;
}
public void setPayDate(Timestamp payDate) {
	this.payDate = payDate;
}
public boolean isPayCk() {
	return payCk;
}
public void setPayCk(boolean payCk) {
	this.payCk = payCk;
}
public int getCarCode() {
	return carCode;
}
public void setCarCode(int carCode) {
	this.carCode = carCode;
}

}
