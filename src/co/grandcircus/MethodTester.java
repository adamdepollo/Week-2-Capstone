package co.grandcircus;

import java.util.Scanner;

public class MethodTester {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println(Validator.getDate(scnr, "Enter date: "));
	}

}
