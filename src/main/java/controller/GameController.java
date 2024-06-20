package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Control;
import model.Fire;
import model.Game;
import model.User;
import model.construction.Building;
import model.construction.Construction;
import model.construction.House;
import model.motile.Falcon;
import model.motile.Mig;
import model.motile.MovingObject;
import model.motile.bomb.Bomb;
import model.motile.vehicle.Tank;
import model.motile.vehicle.Truck;
import view.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameController {
	public static final double migAlertTime = 4;
	private static boolean upKeyPressed;
	private static boolean downKeyPressed;
	private static boolean leftKeyPressed;
	private static boolean rightKeyPressed;
	private static boolean spaceKeyPressed;
	private static boolean rKeyPressed;
	private static boolean tKeyPressed;
	private static boolean pKeyPressed;
	private static double timeLeft;
	private static boolean isFalconHit;
	public static Game game;
	private static Timeline falconFading;
	public static void startGame() {
		boolean isNull = false;
		if (game == null) {
			GameController.game = new Game(User.getLoggedInUser());
			isNull = true;
		}
		GameLauncher.getController().getPane().getChildren().add(GameController.game.getFloor());
		GameLauncher.getController().getPane().getChildren().add(GameController.game.getFalcon());
		for (Node node : GameController.game.getNodes()) {
			GameLauncher.getController().add(node);
		}
		for (Node child : GameLauncher.getController().getPane().getChildren()) {
			child.setOpacity(1);
		}
		upKeyPressed = false;
		downKeyPressed = false;
		leftKeyPressed = false;
		rightKeyPressed = false;
		spaceKeyPressed = false;
		rKeyPressed = false;
		tKeyPressed = false;
		pKeyPressed = false;
		isFalconHit = false;
		if (isNull) init();
	}

	public static void addTank(double seconds) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds)));
		timeline.setCycleCount(1);
		timeline.setOnFinished(actionEvent -> {
			Tank tank = new Tank(game.getFloor(), game.getDifficultyLevel());
			game.getNodes().add(tank);
			GameLauncher.getController().add(tank);
		});
		timeline.play();
	}

	public static void addTruck(double seconds) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds)));
		timeline.setCycleCount(1);
		timeline.setOnFinished(actionEvent -> {
			Truck truck = new Truck(game.getFloor());
			game.getNodes().add(truck);
			GameLauncher.getController().add(truck);
		});
		timeline.play();
	}

	public static void addMig(double seconds) {
		Timeline alertTimeline = new Timeline(new KeyFrame(Duration.seconds(seconds - game.getMigAlertTime())));
		alertTimeline.setCycleCount(1);
		alertTimeline.setOnFinished(actionEvent -> InfoTableController.getController().showMigAlert(game.getMigAlertTime()));
		alertTimeline.play();
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds)));
		timeline.setCycleCount(1);
		timeline.setOnFinished(actionEvent -> {
			Mig mig = new Mig(game.getDifficultyLevel());
			game.getNodes().add(mig);
			GameLauncher.getController().add(mig);
		});
		timeline.play();
	}

	public static void init() {
		int houseCount = Main.random.nextInt(1, 4);
		int buildingCount = Main.random.nextInt(1, 4);
		int count = houseCount + buildingCount;
		double block = GameLauncher.WIDTH / count;
		for (int i = 0; i < count; i++) {
			if (houseCount != 0 && (buildingCount == 0 || Main.random.nextBoolean())) {
				double x = Main.random.nextDouble(i * block, (i + 1) * block - House.WIDTH);
				House house = new House(game.getFloor(), x);
				game.getNodes().add(house);
				GameLauncher.getController().add(house);
				--houseCount;
			} else {
				double x = Main.random.nextDouble(i * block, (i + 1) * block - Building.WIDTH);
				Building building = new Building(game.getFloor(), x);
				game.getNodes().add(building);
				GameLauncher.getController().add(building);
				--buildingCount;
			}
		}
		timeLeft = 0;
		for (int i = 0; i < Main.random.nextInt(3, 6); i++) {
			double t = Main.random.nextDouble(0, 15);
			timeLeft = Math.max(timeLeft, t);
			addTank(t);
		}
		for (int i = 0; i < Main.random.nextInt(2, 6); i++) {
			double t = Main.random.nextDouble(0, 15);
			timeLeft = Math.max(timeLeft, t);
			addTruck(t);
		}
		if (game.getWave() == 3) {
			for (int i = 0; i < Main.random.nextInt(1, 4); i++) {
				double t = Main.random.nextDouble(game.getMigAlertTime(), 15);
				timeLeft = Math.max(timeLeft, t);
				addMig(t);
			}
		}
	}

	public static void handleFalconActions(KeyEvent keyEvent) {
		Control control = game.getUser().getControlKeys();
		if (keyEvent.getEventType().equals(KeyEvent.KEY_PRESSED)) {
			if (keyEvent.getCode() == control.getRight() && !rightKeyPressed) {
				game.getFalcon().giveHAcceleration(Falcon.acceleration);
				rightKeyPressed = true;
			} else if (keyEvent.getCode() == control.getLeft() && !leftKeyPressed) {
				game.getFalcon().giveHAcceleration(-Falcon.acceleration);
				leftKeyPressed = true;
			} else if (keyEvent.getCode() == control.getUp() && !upKeyPressed) {
				game.getFalcon().giveVAcceleration(-Falcon.acceleration);
				upKeyPressed = true;
			} else if (keyEvent.getCode() == control.getDown() && !downKeyPressed) {
				game.getFalcon().giveVAcceleration(Falcon.acceleration);
				downKeyPressed = true;
			} else if (keyEvent.getCode() == KeyCode.SPACE && !spaceKeyPressed) {
				spaceKeyPressed = true;
				GameLauncher.getController().add(game.launchMissile());
			} else if (keyEvent.getCode() == KeyCode.R && !rKeyPressed) {
				rKeyPressed = true;
				if (game.getAtomicBombs() > 0) {
					GameLauncher.getController().add(game.launchAtomicBomb());
					game.addAtomicBomb(-1);
				}
			} else if (keyEvent.getCode() == KeyCode.T && !tKeyPressed) {
				tKeyPressed = true;
				Tank tank = new Tank(game.getFloor(), game.getDifficultyLevel());
				tank.setX(Main.random.nextDouble(0, GameLauncher.WIDTH));
				game.getNodes().add(tank);
				GameLauncher.getController().add(tank);
			} else if (keyEvent.getCode() == KeyCode.P && !pKeyPressed) {
				pKeyPressed = true;
				game.finishWave();
			}
		}
		if (keyEvent.getEventType().equals(KeyEvent.KEY_RELEASED)) {
			if (keyEvent.getCode() == control.getRight() && rightKeyPressed) {
				game.getFalcon().giveHAcceleration(-Falcon.acceleration);
				rightKeyPressed = false;
			} else if (keyEvent.getCode() == control.getLeft() && leftKeyPressed) {
				game.getFalcon().giveHAcceleration(Falcon.acceleration);
				leftKeyPressed = false;
			} else if (keyEvent.getCode() == control.getUp() && upKeyPressed) {
				game.getFalcon().giveVAcceleration(Falcon.acceleration);
				upKeyPressed = false;
			} else if (keyEvent.getCode() == control.getDown() && downKeyPressed) {
				game.getFalcon().giveVAcceleration(-Falcon.acceleration);
				downKeyPressed = false;
			} else if (keyEvent.getCode() == KeyCode.SPACE) {
				spaceKeyPressed = false;
			} else if (keyEvent.getCode() == KeyCode.R) {
				rKeyPressed = false;
			} else if (keyEvent.getCode() == KeyCode.T) {
				tKeyPressed = false;
			} else if (keyEvent.getCode() == KeyCode.P) {
				pKeyPressed = false;
			}
		}
	}

	public static void falconCollision(Point2D point) {
		if (isFalconHit) return;
		isFalconHit = true;
		Falcon falcon = game.getFalcon();
		AtomicBoolean isFading = new AtomicBoolean(true);
		falconFading = new Timeline(new KeyFrame(Duration.millis(10), actionEvent -> {
			if (falcon.getOpacity() >= 0.99) isFading.set(true);
			else if (falcon.getOpacity() <= 0.5) isFading.set(false);
			if (isFading.get()) falcon.setOpacity(falcon.getOpacity() - 0.02);
			else falcon.setOpacity(falcon.getOpacity() + 0.02);
		}));
		falconFading.setCycleCount(300);
		game.getFalcon().setOnKeyPressed(keyEvent -> {
			if (keyEvent.getCode() == KeyCode.H) {
				falconFading.stop();
				isFalconHit = false;
				game.getFalcon().setOpacity(1);
			} else handleFalconActions(keyEvent);
		});
		falconFading.setOnFinished(actionEvent -> {
			isFalconHit = false;
			game.reduceHP();
			game.getFalcon().setOnKeyPressed(GameController::handleFalconActions);
		});
		falconFading.play();
	}

	public static void updateWave() {
		InfoTableController.getController().wave.setText(Objects.toString(game.getWave()));
	}

	public static void updateHP() {
		InfoTableController.getController().hp.setText(Objects.toString(game.getHP()));
	}

	public static void updateKills() {
		InfoTableController.getController().kills.setText(Objects.toString(game.getKills()));
	}

	public static void updateAccuracy() {
		InfoTableController.getController().accuracy.setText(Objects.toString((int) (game.getAccuracy() * 100)) + "%");
	}

	public static void updateAtomicBombs() {
		InfoTableController.getController().radioActive.setText(Objects.toString(game.getAtomicBombs()));
	}

	public static void updateClusters() {
		InfoTableController.getController().cluster.setText(Objects.toString(game.getClusters()));
	}

	public static void handleCollisions() {
		for (Bomb bomb : game.getBombs()) {
			bomb.checkExplosion(game);
		}
		ArrayList<Node> nodes = new ArrayList<>(game.getNodes());
		for (Node node : nodes) {
			if (!(node instanceof Bomb || node instanceof Fire) && game.getFalcon().intersects(node.getBoundsInLocal())) game.die();
		}
		if (game.getFalcon().intersects(game.getFloor().getBoundsInLocal())) game.die();
	}

	public static void handleMoves() {
		ArrayList<Node> nodes = new ArrayList<>(game.getNodes());
		game.getFalcon().move();
		for (Node node : nodes) {
			if (node instanceof MovingObject) ((MovingObject) node).move();
		}
	}

	public static void handleEnemyShoots() {
		if (game.getWave() != 1) {
			for (Tank tank : game.getTanks()) {
				if (!tank.isBurning() && tank.getTimePassedLastShot() > 1000 && tank.getCenter().distance(game.getFalcon().getCenter()) < 100 * game.getDifficultyLevel())
					GameLauncher.getController().add(game.tankShoot(tank));
			}
			for (Mig mig : game.getMigs()) {
				if (mig.getTimePassedLastShot() > 1000 && mig.getCenter().distance(game.getFalcon().getCenter()) < 100 * game.getDifficultyLevel())
					GameLauncher.getController().add(game.migShoot(mig));
			}
		}
	}

	public static void handlePhysics() {
		handleMoves();
		handleCollisions();
		handleEnemyShoots();
		timeLeft -= 0.02;
		if (timeLeft <= 0 && game.getNodes().isEmpty()) game.finishWave();
	}

	public static void remove(Node node) {
		if (node instanceof Tank) game.addKills(5);
		if (node instanceof Truck) game.addKills(3);
		if (node instanceof Construction) game.addKills(2);
		game.getNodes().remove(node);
		GameLauncher.getController().getPane().getChildren().remove(node);
	}

	public static void endGame(boolean win) {
		if (falconFading != null) falconFading.stop();
		for (Node child : GameLauncher.getController().getPane().getChildren()) {
			child.setOpacity(0.5);
		}
		InfoTableController.getController().setOpacity(0);
		PauseMenuController.setIconOpacity(0);
		EndGameMenuController.start();
		String lastWave;
		switch (game.getWave()) {
			case 1 -> lastWave = "1st";
			case 2 -> lastWave = "2nd";
			case 3 -> lastWave = "3rd";
			default -> lastWave = "";
		}
		EndGameMenuController.getController().status.setText("You " + (win ? "won!" : "lost!"));
		EndGameMenuController.getController().wave.setText(lastWave + " wave");
		EndGameMenuController.getController().kills.setText(Objects.toString(game.getKills()));
		EndGameMenuController.getController().accuracy.setText(Objects.toString((int) (game.getAccuracy() * 100)) + "%");
		if (!game.getUser().isGuest()) Game.getGames().add(game);
	}

	public static void saveGame() {
		game.getUser().setIncompleteGame(game);
	}
}
