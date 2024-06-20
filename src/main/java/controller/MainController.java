package controller;

import model.User;

public class MainController {
	public static void logout() {
		if (User.getLoggedInUser().isGuest()) User.getLoggedInUser().deleteAccount();
		User.setLoggedInUser(null);
	}
}
