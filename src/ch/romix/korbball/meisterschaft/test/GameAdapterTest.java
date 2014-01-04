package ch.romix.korbball.meisterschaft.test;

import java.util.LinkedList;

import android.test.AndroidTestCase;
import ch.romix.korbball.meisterschaft.Game;
import ch.romix.korbball.meisterschaft.GameAdapter;
import ch.romix.korbball.meisterschaft.R;

public class GameAdapterTest extends AndroidTestCase {

	public GameAdapterTest() {
		super();
	}

	public void testCount() {
		LinkedList<Game> games = new LinkedList<Game>();
		games.add(createGame());
		games.add(createGame());
		GameAdapter adapter = new GameAdapter(getContext(), games, "Team");
		assertEquals("Expected one for the day and two for the games", 3, adapter.getCount());
	}

	public void testGetDataObjectAtCorrectPosition() {
		LinkedList<Game> games = new LinkedList<Game>();
		Game firstGame = createGame("firstA", "firstB");
		games.add(firstGame);
		Game secondGame = createGame("secondA", "secondB");
		games.add(secondGame);
		GameAdapter adapter = new GameAdapter(getContext(), games, "Team");
		assertEquals(firstGame.getDay(), adapter.getItem(0));
		assertEquals(firstGame, adapter.getItem(1));
		assertEquals(secondGame, adapter.getItem(2));
	}

	public void testViewCreation() {
		LinkedList<Game> games = new LinkedList<Game>();
		Game game = createGame("teamA", "teamB");
		games.add(game);
		GameAdapter adapter = new GameAdapter(getContext(), games, game.getTeamA());
		assertEquals(R.id.DayItem, adapter.getView(0, null, null).getId());
		assertEquals(R.id.GameItem, adapter.getView(1, null, null).getId());
	}

	private Game createGame(String teamA, String teamB) {
		Game game = createGame();
		game.setTeamA(teamA);
		game.setTeamB(teamB);
		return game;
	}

	private Game createGame() {
		Game game = new Game();
		game.setDay("01.01.2014");
		return game;
	}
}
