## [OOP 연습 01] 문자열 계산기

## 00. 개요

> 본 게시글은 OOP & Refactoring 연습 시리즈로 **자바지기 박재성 강사님의 클린코드를 위한 Refactoring with JAVA** 강의에서 진행한 토이프로젝트를 재구현하며 정리하는 내용입니다.
강의 내용이 궁금하시면 해당 강좌를 수강바랍니다.

구현할 토이프로젝트 순서는 아래와 같습니다.
- 문자열 계산기(소스코드 url : [Github](https://github.com/hy43/))
- 레이싱 게임
- 로또 생성기
- 볼링 게임

## 01. 요구 사항
* 기능 요구 사항
    - 사용자가 입력한 문자열 값에 따라 사칙연산을 수행할 수 있는 계산기를 구현해야 한다.
    - 문자열 계산기는 사칙연산의 계산 우선순위가 아닌 입력 값에 따라 계산 순서가 결정된다.
    즉, 수학에서는 곱셈, 나눗셈이 덧셈, 뺄셈 보다 먼저 계산해야 하지만 이를 무시한다.
    - 예를 들어 "2 + 3 * 4 / 2"와 같은 문자열을 입력할 경우 2 + 3 * 4 / 2 실행 결과인 10을 출력해야 한다.
* 프로그래밍 요구 사항
    - 메소드가 너무 많은 일을 하지 않도록 분리하기 위해 노력해 본다.
    - 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기만 한다.
    - 규칙 2: else 예약어를 쓰지 않는다.
* 추가 요구 사항 (기본 기능/프로그래밍 요구 사항을 충족시킨 경우에 진행)
    - 사칙 연산을 구현하면서 4개의 if문을 사용하는 코드가 발생한다.
    - 모든 if문을 제거해 본다.
    - 힌트 : 자바의 다형성을 활용한다. interace와 Map 활용

## 02. 요구 사항 구현

> 한번에 모든 요구 사항을 만족 시키기 보다는 기능 요구 사항을 하나씩 만족시키면서 프로그래밍 요구 사항에 대한 리팩토링을 진행하는 것이 좋다.

* 사용자가 입력한 문자열 값에 따라 사칙연산을 수행할 수 있는 계산기를 구현해야 한다.
    - 사용자 입력 구현
    - 사용자가 입력한 문자열을 공백을 기준으로 숫자와 연산자로 구분
    - 연산 결과 출력
* 사용자 입력 부<br/>
**StringCalculator.java** <br/>
```
public class StringCalculator {
    public StringCalculator() {
        System.out.print("연산식을 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        String[] inputValues = scanner.nextLine().split(" "); 
    }
}
```
    
Scanner API를 이용하여 사용자로부터 값을 입력 받고, String Class에 포함된 split 메소드를 사용하여 백 기준으로 문자열을 분리한다. 
예를 들어 사용자가 "2 + 3 * 2"를 입력하면 2,+,3,*,2 와 같이 5개의 인자를 갖는 String 배열이 반환된다.

* 이제 각 연산자 별 연산에 대한 기능을 구현해보도록 하자.  <br/>
**StringCalculator.java**    
```
public class StringCalculator {
    public StringCalculator() {
        System.out.print("연산식을 입력하세요 : ");
        Scanner scanner = new Scanner(System.in);
        String[] inputValues = scanner.nextLine().split(" ");
            
        int result = Integer.parseInt(inputValues[0]);
        for(int i=1;i<inputValues.length;i+=2) {
            if("+".equals(inputValues[i])) {
                result += Integer.parseInt(inputValues[i+1]);
            }else if("-".equals(inputValues[i])) {
                result -= Integer.parseInt(inputValues[i+1]);
            }else if("*".equals(inputValues[i])) {
                result *= Integer.parseInt(inputValues[i+1]);
            }else if("/".equals(inputValues[i])) {
                result /= Integer.parseInt(inputValues[i+1]);
            }
        }
        System.out.println("연산 결과는 " + result + "입니다.");
    }
}

```

연산자는 무조건 두번째 인자 즉, 배열의 인덱스 1번부터 3,5,7과 같은 홀수번째에 위치하므로 for문에서 2씩 더하여 연산자만 걸러내는 작업을 할 수 있다.
    
또한 if를 통해 각 사칙연산에 대해 String 값을 받아 그에 맞는 연산을 진행해주면 된다.

## 03. Refactoring (프로그래밍 요구 사항)

> view 분리

* 현재 문자열 계산기는 StringCalculator 객체를 생성할때 생성자에서 모든 작업을 처리한다. 
프로그래밍 요구 사항을 준수하기 위해서는 각각의 기능 단위로 메소드 or 클래스를 분리한다.
* 첫번째 작업으로 뷰와 비즈니스 로직을 분리해보도록 하자
    - 뷰 : System.out.println
    - 비즈니스 로직 : 입력 받은 문자열을 분리하고 계산하는 로직
    - 유틸 로직 : 문자열 입력, 문자열->숫자 변환     

**InputView.java**

```
public class InputView {
	public static String input() {
		System.out.println("연산식을 입력해주세요 : ");
		return new Scanner(System.in).nextLine();
	}
}
```     

**OutputView.java**

```
public class OutputView {
	public static void output(int result) {
		System.out.println("연산의 결과는 " + result + "입니다.");
	}
}
```

InputView와 OutputView는 별도로 객체 생성을 하지 않기 위해 static 메소드를 활용한다.

> 비즈니스 로직

view를 분리했으므로 입력받은 데이터를 분할하고 계산하는 로직을 분리해보자.

**StringCalculator.java**

```
public class StringCalculator {
	public int calculate(String input) {
		String[] inputValues = input.split(" ");
		int result = Integer.parseInt(inputValues[0]);
        for(int i=1;i<inputValues.length;i+=2) {
        	if("+".equals(inputValues[i])) {
        		result += Integer.parseInt(inputValues[i+1]);
        	}else if("-".equals(inputValues[i])) {
        		result -= Integer.parseInt(inputValues[i+1]);
        	}else if("*".equals(inputValues[i])) {
        		result *= Integer.parseInt(inputValues[i+1]);
        	}else if("/".equals(inputValues[i])) {
        		result /= Integer.parseInt(inputValues[i+1]);
        	}
        }
		return result;
	}
}
```

> 비즈니스 로직 리팩토링

로직은 분리 되었으나.. 프로그래밍 요구사항에 의하면 한개 메소드는 한번의 들여쓰기만을 만족하도록 구현되어야 한다.

**StringCalculator.java**

```
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
```

> Controller Class 생성

현재까지 구현된 로직을 실행하기 위한 Controller class를 구현하도록 하자.

**ApplicationMain.java**

```
public class ApplicationMain {
	public static void main(String[] args) {
		StringCalculator stringCalculator = new StringCalculator();
		OutputView.output(stringCalculator.calculate(InputView.input()));
	}
}
```

여기까지 문자열 계산기에 대한 구현이 완료되었다. <br/>
