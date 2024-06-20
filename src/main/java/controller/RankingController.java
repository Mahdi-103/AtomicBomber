package controller;

import model.Game;

import java.util.ArrayList;

public class RankingController {
	public static ArrayList<Game> sortGames(String sorter) {
		ArrayList<Game> games = new ArrayList<>(Game.getGames());
		if (sorter.equals("Kills")) games.sort((Game a, Game b) -> {
			if (a.getKills() != b.getKills()) return b.getKills() - a.getKills();
			return b.getWave() - a.getWave();
		});
		else if (sorter.equals("Difficulty")) games.sort((Game a, Game b) -> {
			if (a.getKills() * a.getDifficultyLevel() != b.getKills() * b.getDifficultyLevel())
				return b.getKills() * b.getDifficultyLevel() - a.getKills() * a.getDifficultyLevel();
			return b.getWave() - a.getWave();
		});
		else games.sort((Game a, Game b) -> {
				if (a.getAccuracy() != b.getAccuracy())
					return (int) (b.getAccuracy() * 100) - (int) (a.getAccuracy() * 100);
				return b.getWave() - a.getWave();
			});
		if (games.size() > 10) games = (ArrayList<Game>) games.subList(0, 10);
		return games;
	}
}
