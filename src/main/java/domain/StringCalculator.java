package domain;

public class StringCalculator {
	private int result;
	public int calculate(String input) {
		String[] inputValues = input.split(" ");
		this.result = Integer.parseInt(inputValues[0]);
        for(int i=1;i<inputValues.length;i+=2) {
        	result = findOperator(inputValues[i], Integer.parseInt(inputValues[i+1]));
        }
		return result;
	}

	private int findOperator(String operator, int targetNumber) {
		if("+".equals(operator)) {
			result += targetNumber;
		}else if("-".equals(operator)) {
			result -= targetNumber;
		}else if("*".equals(operator)) {
			result *= targetNumber;
		}else if("/".equals(operator)) {
			result /= targetNumber;
		}
		return result;
	}
}
