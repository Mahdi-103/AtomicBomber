package model;

import controller.GameController;
import javafx.scene.Node;
import model.motile.Falcon;
import model.motile.Mig;
import model.motile.bomb.*;
import model.motile.vehicle.Tank;
import model.motile.vehicle.Vehicle;
import view.GameLauncher;

import java.util.ArrayList;

public class Game {
	private final User user;
	private final Falcon falcon;
	private final Floor floor;
	private final ArrayList<Node> nodes;
	private int hp;
	private int atomicBombs;
	private int clusters;
	private int hits;
	private int explodedBombs;
	private int kills;
	private int wave;
	private final int difficultyLevel;
	private static final ArrayList<Game> games = new ArrayList<>();

	public Game(User user) {
		hp = 3;
		wave = 1;
		this.user = user;
		atomicBombs = 0;
		clusters = 0;
		hits = 0;
		explodedBombs = 0;
		this.falcon = new Falcon();
		this.floor = new Floor(100);
		this.nodes = new ArrayList<>();
		this.difficultyLevel = user.getDifficultyLevel();
	}

	public void reduceHP() {
		--hp;
		GameController.updateHP();
		if (hp == 0) GameController.endGame(false);
	}

	public void die() {
		hp = 0;
		GameController.updateHP();
		GameController.endGame(false);
	}

	public int getHP() {
		return hp;
	}

	public Missile launchMissile() {
		Missile missile = this.falcon.launchMissile();
		this.nodes.add(missile);
		return missile;
	}

	public AtomicBomb launchAtomicBomb() {
		AtomicBomb atomicBomb = this.falcon.launchAtomicBomb();
		this.nodes.add(atomicBomb);
		return atomicBomb;
	}

	public Shell tankShoot(Tank tank) {
		Shell shell = tank.shoot(this.falcon);
		this.nodes.add(shell);
		return shell;
	}

	public Mikoyan migShoot(Mig mig) {
		Mikoyan mikoyan = mig.shoot(this.falcon);
		this.nodes.add(mikoyan);
		return mikoyan;
	}

	public User getUser() {
		return user;
	}

	public Falcon getFalcon() {
		return falcon;
	}

	public Floor getFloor() {
		return floor;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Bomb> getBombs() {
		ArrayList<Bomb> bombs = new ArrayList<>();
		for (Node node : nodes) {
			if (node instanceof Bomb) bombs.add((Bomb) node);
		}
		return bombs;
	}

	public ArrayList<Vehicle> getVehicles() {
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		for (Node node : nodes) {
			if (node instanceof Vehicle) vehicles.add((Vehicle) node);
		}
		return vehicles;
	}

	public ArrayList<Tank> getTanks() {
		ArrayList<Tank> tanks = new ArrayList<>();
		for (Node node : nodes) {
			if (node instanceof Tank) tanks.add((Tank) node);
		}
		return tanks;
	}

	public ArrayList<Mig> getMigs() {
		ArrayList<Mig> migs = new ArrayList<>();
		for (Node node : nodes) {
			if (node instanceof Mig) migs.add((Mig) node);
		}
		return migs;
	}

	public void addAtomicBomb(int atomicBombCount) {
		this.atomicBombs += atomicBombCount;
		GameController.updateAtomicBombs();
	}

	public int getAtomicBombs() {
		return atomicBombs;
	}

	public void addCluster(int clusterCount) {
		this.clusters += clusterCount;
		GameController.updateClusters();
	}

	public int getClusters() {
		return clusters;
	}

	public void addHits(int hitCounts) {
		this.hits += hitCounts;
		GameController.updateAccuracy();
	}

	public int getHits() {
		return hits;
	}

	public void explodeBomb() {
		this.explodedBombs++;
		GameController.updateAccuracy();
	}

	public int getExplodedBombs() {
		return explodedBombs;
	}

	public double getAccuracy() {
		return (double) this.getHits() / this.getExplodedBombs();
	}

	public void addKills(int killCount) {
		this.kills += killCount;
		GameController.updateKills();
	}

	public int getKills() {
		return kills;
	}

	public int getWave() {
		return wave;
	}


	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public double getMigAlertTime() {
		double migAlertTime = GameController.migAlertTime;
		switch (difficultyLevel) {
			case 2 -> migAlertTime *= 0.75;
			case 3 -> migAlertTime *= 0.5;
		}
		return migAlertTime;
	}

	public void finishWave() {
		if (wave == 3) {
			GameController.endGame(true);
			return;
		}
		ArrayList<Node> nodes = new ArrayList<>(this.nodes);
		for (Node node : nodes) {
			this.nodes.remove(node);
			GameLauncher.getController().getPane().getChildren().remove(node);
		}
		++wave;
		GameController.init();
		GameController.updateWave();
	}

	public static ArrayList<Game> getGames() {
		return games;
	}
}
