package domain;

import view.InputView;
import view.OutputView;

public class ApplicationMain {
	public static void main(String[] args) {
		StringCalculator stringCalculator = new StringCalculator();
		OutputView.output(stringCalculator.calculate(InputView.input()));
	}
}
