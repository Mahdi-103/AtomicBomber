package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.User;

import java.io.File;
import java.net.URL;
import java.util.*;


public class Main extends Application {

	public static final Random random = new Random();
	public static Stage appStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		appStage = stage;
		StartMenuController.start();
	}

	public static Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String header, String content) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait();
	}

	public static File pickRandom(String path) {
		File[] files = (new File(path)).listFiles();
		assert files != null;
		return files[random.nextInt(files.length)];
	}

	public static void setBackground(Pane pane) {
		Image image;
		try {
			image = new Image(pickRandom(Objects.requireNonNull(Main.class.getResource("/images/backgrounds/menus")).getPath()).toURI().toURL().toString());
		} catch (Exception e) {
			return;
		}
		setBackground(pane, image);
	}

	public static void show(Stage stage, URL paneUrl) throws Exception {
		Pane root = FXMLLoader.load(paneUrl);
		Main.setBackground(root);
		Platform.runLater(root::requestFocus);
		stage.setScene(new Scene(root));
		stage.show();
		Main.setColor();
	}

	public static void show(Stage stage, URL paneUrl, Image image) throws Exception {
		Pane root = FXMLLoader.load(paneUrl);
		Main.setBackground(root, image);
		Platform.runLater(root::requestFocus);
		stage.setScene(new Scene(root));
		stage.show();
		Main.setColor();
	}

	public static void setBackground(Pane pane, Image image) {
		BackgroundSize backgroundSize = new BackgroundSize(pane.getPrefWidth(), pane.getPrefHeight(), false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);
		pane.setBackground(background);
	}

	public static void setColor() {
		ColorAdjust grayScale = new ColorAdjust();
		if (User.getLoggedInUser() != null && User.getLoggedInUser().isNoir()) grayScale.setSaturation(-1);
		else grayScale.setSaturation(0);
		Main.appStage.getScene().getRoot().setEffect(grayScale);
	}
}
