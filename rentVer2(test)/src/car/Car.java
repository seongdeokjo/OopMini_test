package car;

public class Car {
	private int idx;
	private String carName;
	private int carNumber;
	private int carPrice;
	private boolean useCar;

	public Car(int idx, String carName, int carNumber, int carPrice, boolean useCar) {
		this.idx = idx;
		this.carName = carName;
		this.carNumber = carNumber;
		this.carPrice = carPrice;
		this.useCar = useCar;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public int getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(int carNumber) {
		this.carNumber = carNumber;
	}

	public int getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(int carPrice) {
		this.carPrice = carPrice;
	}

	public boolean isUseCar() {
		return useCar;
	}

	public void setUseCar(boolean useCar) {
		this.useCar = useCar;
	}

}
