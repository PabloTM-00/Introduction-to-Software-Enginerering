import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {

	private StringCalculator calculator;

	@BeforeEach
	void setUp() {
		calculator = new StringCalculator();
	}

	@DisplayName("If the list of numbers is empty the result is zero")
	@Test
	void givenAnEmptyStringWhenAddThenReturnZero() {
		int expectedValue = 0;
		int actualValue = calculator.add("");
		assertEquals(expectedValue, actualValue);
	}

	@DisplayName("If the string has one number it returns that number")
	@Test
	void givenASingleNumberStringWhenAddThenReturnThatNumber() {
		int expectedValue = 5;
		int actualValue = calculator.add("5");
		assertEquals(expectedValue, actualValue);
	}

	@DisplayName("The string has two numbers separated by a comma")
	@Test
	void givenStringWithTwoNumbersSeparatedByAComma(){
		int expectedValue = 3;
		int actualValue = calculator.add("1,2");
		assertEquals(expectedValue,actualValue);
	}

	@DisplayName("The string has several numbers separated by commas")
	@Test
	void givenStringWithSeveralNumbersSeparatedByAComma(){
		int expectedValue = 6;
		int actualValue = calculator.add("1,2,3");
		assertEquals(expectedValue,actualValue);
	}

	@DisplayName("The string has several numbers separated by commas or end of line")
	@Test
	void givenStringWithSeveralNumbersSeparatedByCommasOrEndOfLine() {
		int expectedValue = 10;
		int actualValue = calculator.add("1,2\n3,4");
		assertEquals(expectedValue, actualValue);
	}

	@DisplayName("The string can use a user defined s delimiter character with a prefix of the form: ''//s\\n\'")
	@Test
	void givenStringWithSeveralNumbersSeparatedByUserDefinedDelimiter() {
		int expectedValue = 6;
		int actualValue = calculator.add("//;\n1;2;3");
		assertEquals(expectedValue, actualValue);
	}
	@DisplayName("If a negative number appears in the string an exception is raised")
	@Test
	void givenStringWithNegativeNumberExceptionThrown() {
		boolean thrown = false;

		try {
			calculator.add("1,2,-3");
		} catch (NegativeNumbersException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}

	@DisplayName("If a negative number appears in the string an exception is raised with message")
	@Test
	void givenStringWithNegativeNumberThenExceptionIsThrownWithMessage() {
		try {
			calculator.add("1,-2,3");
			fail("Expected NegativeNumberException to be thrown");
		} catch (NegativeNumbersException e) {
			assertEquals("Negative numbers not allowed", e.getMessage());
		}
	}
}