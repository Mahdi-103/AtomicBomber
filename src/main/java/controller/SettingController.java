package controller;

import model.User;
import view.Main;

public class SettingController {

	public static void setDifficulty(String difficulty) {
		if (difficulty.equals("Easy")) User.getLoggedInUser().setDifficultyLevel(1);
		else if (difficulty.equals("Medium")) User.getLoggedInUser().setDifficultyLevel(2);
		else User.getLoggedInUser().setDifficultyLevel(3);
	}

	public static String updateColor() {
		User.getLoggedInUser().setNoir(!User.getLoggedInUser().isNoir());
		Main.setColor();
		if (User.getLoggedInUser().isNoir()) return "Noir";
		else return "Colorful";
	}

	public static String updateControlKeys() {
		User.getLoggedInUser().changeControlKey();
		return User.getLoggedInUser().getControlKeys().toString();
	}

}
