package view;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameLauncher extends Application {
	public static final double WIDTH = 1000, HEIGHT = 600;
	private static final GameLauncher controller = new GameLauncher();
	private Pane pane;
	private Timeline actions;

	public static void start() {
		try {
			controller.start(Main.appStage);
		} catch (Exception ignored) {
		}
	}

	private void setSize() {
		pane.setMinWidth(WIDTH);
		pane.setPrefWidth(WIDTH);
		pane.setMaxWidth(WIDTH);
		pane.setMinHeight(HEIGHT);
		pane.setPrefHeight(HEIGHT);
		pane.setMaxHeight(HEIGHT);
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane = new Pane();
		setSize();
		GameController.startGame();
		PauseMenuController.start();
		InfoTableController.start();
		Scene scene = new Scene(pane, 1000, 600);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		System.out.println(pane.getWidth() + " " + pane.getHeight());
		Main.setColor();
		GameController.game.getFalcon().requestFocus();
		GameController.game.getFalcon().setOnKeyPressed(GameController::handleFalconActions);
		GameController.game.getFalcon().setOnKeyReleased(GameController::handleFalconActions);
		actions = new Timeline(new KeyFrame(Duration.millis(20), actionEvent -> GameController.handlePhysics()));
		actions.setCycleCount(-1);
		actions.play();
	}

	public void add(Node node) {
		int id = pane.getChildren().indexOf(GameController.game.getFalcon());
		if (id >= 0) pane.getChildren().add(id, node);
		else pane.getChildren().add(node);
	}

	public Pane getPane() {
		return pane;
	}

	public Timeline getActions() {
		return actions;
	}

	public static GameLauncher getController() {
		return controller;
	}
}
