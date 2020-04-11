package view;

import java.util.Scanner;

public class InputView {
	public static String input() {
		System.out.println("연산식을 입력해주세요 : ");
		return new Scanner(System.in).nextLine();
	}
}
