package com.company.parsers;

import com.company.model.Game;
import com.company.model.GameManager;
import com.company.model.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.json.*;
import java.io.StringReader;


public class GamesParser {

	public void parseGames(String jsonResponse) {

		JsonReader reader = Json.createReader(new StringReader(jsonResponse));
		JsonArray gameArray = reader.readArray();

		for(int i = 0; i < gameArray.size(); i++) {
			JsonObject gameObject = gameArray.getJsonObject(i);
			Game game = parseGame(gameObject);
			GameManager.getInstance().addGame(game);
		}
	}


	private Game parseGame(JsonObject gameObject) {
		JsonArray playerNameArray = gameObject.getJsonArray("playerNames");
		JsonArray scoreArray = gameObject.getJsonArray("scores");

		return new Game(parsePlayerNames(playerNameArray), parseScores(scoreArray), Arrays.asList(1, 2, 3, 4));
	}

	private List<Player> parsePlayerNames(JsonArray playerNameArray) {
		List<Player> players = new LinkedList<Player>();

		for(int i = 0; i < playerNameArray.size(); i++) {
			for(Player player : GameManager.getInstance().getPlayers()) {
				if(player.getName().equals(playerNameArray.getString(i))) {
					players.add(player);
				}
			}

			if(players.size() != (i + 1)) {
				players.add(new Player(playerNameArray.getString(i)));
			}
		}

		return players;
	}

	private List<Integer> parseScores(JsonArray scoreArray) {
		List<Integer> scores = new LinkedList<Integer>();

		for(int i = 0; i < scoreArray.size(); i++) {
			scores.add(scoreArray.getInt(i));
		}

		return scores;
	}
}
