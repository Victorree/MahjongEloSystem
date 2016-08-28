package com.company;

import com.company.graphs.NewEloDistribution;
import com.company.graphs.OldEloDistribution;
import com.company.model.EloCalculator;
import com.company.model.GameManager;
import com.company.model.RankingSystemType;
import com.company.parsers.GamesParser;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	    (new Main()).parse();
    }

	private void parse() {
		GameManager.getInstance().setEloCalculator(new EloCalculator(
				2000,
				5,
				Arrays.asList(15000, 0, -5000, -10000),
				RankingSystemType.NEW_SYSTEM));

		try {
			InputStream inputStream = new FileInputStream("./2015games.json");
			String jsonData = readSource(inputStream);
			GamesParser gamesParser = new GamesParser();
			gamesParser.parseGames(jsonData);
		} catch (IOException e) {
			System.out.println("Error reading file...");
			e.printStackTrace();
		}

		// for saving the historical elo graphs
		//for(Player player : GameManager.getInstance().getPlayers()) { PlayerHistoricalElo.saveGraph(player); }

		OldEloDistribution.graph();
		NewEloDistribution.graph();
	}

	/**
	 * Read source data from input stream as string
	 *
	 * @param is  input stream connected to source data
	 * @return  source data as string
	 * @throws IOException  when error occurs reading data from file
	 */
	private String readSource(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;

		while((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		br.close();

		return sb.toString();
	}
}
