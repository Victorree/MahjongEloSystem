package com.company.model;

import java.util.LinkedList;
import java.util.List;

public class EloCalculator {
	private int n;
	private int exp;
	private List<Integer> placingAdjustments;
	private RankingSystemType rankingSystemType;

	public EloCalculator(int n, int exp, List<Integer> placingAdjustments, RankingSystemType rankingSystemType) {
		this.n = n;
		this.exp = exp;
		this.placingAdjustments = placingAdjustments;
		this.rankingSystemType = rankingSystemType;
	}

	public void eloGameAdjust(Game game) {
		List<Double> expectedScores = expectedScores(game);
		List<Double> adjustedScores = adjustedScores(game);

		for(int i = 0; i < 4; i++) {
			Player player = game.getPlayers().get(i);
			double eloChange = eloChange(game, player, expectedScores.get(i), adjustedScores.get(i));
			player.setElo(player.getElo() + eloChange);
		}
	}

	private List<Double> expectedScores(Game game) {
		double rawExpectedScoreSum = 0.0;
		List<Double> rawExpectedScores = new LinkedList<Double>();
		List<Double> expectedScores = new LinkedList<Double>();

		for(Player player : game.getPlayers()) {
			double rawExpectedScore = 1 / (1 + Math.pow(exp, (fieldElo(player, game) - player.getElo()) / n));
			rawExpectedScoreSum += rawExpectedScore;
			rawExpectedScores.add(rawExpectedScore);
		}

		for(Double rawExpectedScore : rawExpectedScores) {
			expectedScores.add(rawExpectedScore / rawExpectedScoreSum);
		}

		return expectedScores;
	}

	private double fieldElo(Player player, Game game) {
		double fieldElo = 0.0;

		for(Player p : game.getPlayers()) {
			if(!player.equals(p)) {
				fieldElo += p.getElo();
			}
		}
		return fieldElo / 3;
	}

	private List<Double> adjustedScores(Game game) {
		List<Double> adjustedScores = new LinkedList<Double>();

		for(int i = 0; i < 4; i++) {
			adjustedScores.add((game.getPoints().get(i) + placingAdjustments.get(i)) / 100000.0);
		}

		return adjustedScores;
	}

	private double eloChange(Game game, Player player, double expectedScore, double adjustedScore) {
		int k;

		switch(rankingSystemType) {
			case JOSH_SYSTEM:
				int gamesPlayed = player.getGames().indexOf(game);
				if(gamesPlayed < 40) {
					k = -2 * gamesPlayed + 200;
				} else {
					k = 120;
				}
				break;
			case NEW_SYSTEM:
				if(player.getElo() < 1300) {
					k = 200;
				} else if(player.getElo() < 1500) {
					k = 180;
				} else if(player.getElo() < 1600) {
					k = 160;
				} else if(player.getElo() < 1700) {
					k = 140;
				} else {
					k = 120;
				}
				break;
			case BLOCK_SYSTEM:
				k = 100;
				break;
			default:
				k = 0;
		}

		return k * (adjustedScore - expectedScore);
	}
}