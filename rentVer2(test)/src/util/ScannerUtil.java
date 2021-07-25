package util;

import java.util.Scanner;

public class ScannerUtil {
	private static Scanner scan = new Scanner(System.in);

	public static String getInputString() {
		return scan.nextLine();
	}

	public static int getInputInteger() {
		return Integer.parseInt(scan.nextLine());
	}
}
