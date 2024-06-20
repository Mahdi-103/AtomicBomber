package model;

import view.Main;

import java.util.ArrayList;
import java.util.Objects;

public class User {
	private String username;
	private String password;
	private String avatarURL;
	private boolean isArrowKeyGamer;
	private boolean isNoir;
	private int difficultyLevel;
	private Game incompleteGame;
	private static final ArrayList<User> users = new ArrayList<>();
	private static User loggedInUser;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		try {
			this.avatarURL = Main.pickRandom(Objects.requireNonNull(Main.class.getResource("/images/avatars/defaults")).getPath()).toURI().toURL().toString();
		} catch (Exception ignored) {
		}
		isArrowKeyGamer = true;
		difficultyLevel = 1;
		users.add(this);
	}

	public static User getUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) return user;
		}
		return null;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
	}

	public boolean isGuest() {
		return this.password.isEmpty();
	}

	public void deleteAccount() {
		users.remove(this);
	}

	public void changeControlKey() {
		isArrowKeyGamer = !isArrowKeyGamer;
	}

	public Control getControlKeys() {
		if (isArrowKeyGamer) return Control.ArrowKey;
		return Control.ASDW;
	}

	public boolean isNoir() {
		return isNoir;
	}

	public void setNoir(boolean noir) {
		isNoir = noir;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public Game getIncompleteGame() {
		return incompleteGame;
	}

	public void setIncompleteGame(Game incompleteGame) {
		this.incompleteGame = incompleteGame;
	}

	public static User getLoggedInUser() {
		return loggedInUser;
	}
	public static void setLoggedInUser(User loggedInUser) {
		User.loggedInUser = loggedInUser;
	}
}
