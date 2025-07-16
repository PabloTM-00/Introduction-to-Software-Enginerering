import java.util.ArrayList;
import java.util.List;

public class StringCalculator {
	public int add(String numbers) {
		int sum = 0;
		if (!numbers.isEmpty()) {
			String[] tokens = separateTokens(numbers);
			List<Integer> values = convertTokensToInt(tokens);
			if (hasNegativeNumber(values)) {
				throw new NegativeNumbersException("Negative numbers not allowed");
			}
			sum = sumNumbers(values);
		}
		return sum;
	}
	private List<Integer> convertTokensToInt(String[] tokens) {
		List<Integer> integers = new ArrayList<>();
		for (String token : tokens) {
			integers.add(Integer.parseInt(token));
		}
		return integers;
	}
	private boolean hasNegativeNumber(List<Integer> values) {
		boolean hasNegative = false;
		for (int number : values) {
			hasNegative = hasNegative || number < 0;
		}
		return hasNegative;
	}
	private String[] separateTokens(String numbers) {
		String delimiter = ",|\n";
		if (numbers.startsWith("//")) {
			delimiter = String.valueOf(numbers.charAt(2));
			numbers = numbers.substring(4);
		}
		return numbers.split(delimiter);
	}
	private int sumNumbers(List<Integer> numbers) {
		int sum = 0;
		for (int number : numbers) {
			sum += number;
		}
		return sum;
	}
}