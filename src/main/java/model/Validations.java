package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Validations {
	VALID_USERNAME("[a-zA-Z_.]+"),
	VALID_PASSWORD(".+");

	private final Pattern pattern;

	Validations(String regex) {
		pattern = Pattern.compile(regex);
	}

	public Matcher getMatcher(String input) {
		return this.pattern.matcher(input);
	}

	public static boolean isValidUsername(String username) {
		return VALID_USERNAME.getMatcher(username).matches();
	}

	public static boolean isValidPassword(String password) {
		return VALID_PASSWORD.getMatcher(password).matches();
	}
}
