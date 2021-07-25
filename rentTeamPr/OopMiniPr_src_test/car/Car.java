package car;

public class Car {
	
	private int carcode;

	private String carnumber;
	private String carname;
	private String carsize;
	private int carseat;
	private int caryear;
	private String fuel;
	private int rentck;

	public Car(int carcode, String carnumber, String carname,
			String carsize, int carseat, int caryear, String fuel,int rentck) {
		this.carcode = carcode;
		this.carnumber = carnumber;
		this.carname = carname;
		this.carsize = carsize;
		this.carseat = carseat;
		this.caryear = caryear;
		this.fuel = fuel;
		this.rentck = rentck;

	}

	public Car(String carnumber, String carname, String carsize, int carseat, int caryear, String fuel) {
		this.carnumber = carnumber;
		this.carname = carname;
		this.carsize = carsize;
		this.carseat = carseat;
		this.caryear = caryear;
		this.fuel = fuel;
	}

	public int getCarcode() {
		return carcode;
	}



	public void setCarcode(int carcode) {
		this.carcode = carcode;
	}



	public String getCarnumber() {
		return carnumber;
	}



	public void setCarnumber(String carnumber) {
		this.carnumber = carnumber;
	}



	public String getCarname() {
		return carname;
	}



	public void setCarname(String carname) {
		this.carname = carname;
	}



	public String getCarsize() {
		return carsize;
	}



	public void setCarsize(String carsize) {
		this.carsize = carsize;
	}



	public int getCarseat() {
		return carseat;
	}



	public void setCarseat(int carseat) {
		this.carseat = carseat;
	}



	public int getCaryear() {
		return caryear;
	}



	public void setCaryear(int caryear) {
		this.caryear = caryear;
	}



	public String getFuel() {
		return fuel;
	}



	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
		
	public int getRentck() {
		return rentck;
	}

	public void setRentck(int rentck) {
		this.rentck = rentck;
	}



	@Override
	public String toString() {
		return "Car [carcode=" + carcode+ ", carnumber=" + carnumber + 
				", carname=" + carname + ", carsize=" + carsize
				+ ", carseat=" + carseat + ", caryear=" + caryear +
				", fuel=" + fuel + "]";
	}
	
	
}
