package ch.romix.korbball.meisterschaft.test;

import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import ch.romix.korbball.meisterschaft.Game;
import ch.romix.korbball.meisterschaft.GameUIData;
import ch.romix.korbball.meisterschaft.R;

public class GameUIDataTest extends AndroidTestCase {

	private LayoutInflater inflater;

	@Override
	protected void setUp() throws Exception {
		inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void testCreationOfDayView() throws Exception {
		String day = "04.01.2014";
		GameUIData dayUIData = GameUIData.createDay(day);
		assertEquals(day, dayUIData.getData());

		View dayView = dayUIData.getView(inflater, null, getContext());
		TextViewAsserts.assertText(day + ":", dayView, R.id.game_day);
	}

	public void testCreationOfGameView() throws Exception {
		Game game = new Game();
		game.setHall("Halle 1");
		game.setPlayed(true);
		game.setResultA(5);
		game.setResultB(3);
		game.setRound("Vorrunde");
		game.setTeamA("TeamA");
		game.setTeamB("TeamB");
		game.setTime("10:00");
		GameUIData gameUIData = GameUIData.createGame(game, game.getTeamA());
		assertEquals(game, gameUIData.getData());

		View gameView = gameUIData.getView(inflater, null, getContext());
		TextViewAsserts.assertText(game.getHall(), gameView, R.id.game_hall);
		TextViewAsserts.assertText(game.getRound(), gameView, R.id.game_round);
		TextViewAsserts.assertText(game.getTeamA() + " - " + game.getTeamB(), gameView, R.id.game_teams);
		TextViewAsserts.assertText(game.getTime(), gameView, R.id.game_time);
		TextViewAsserts.assertText(game.getResultA() + " : " + game.getResultB(), gameView, R.id.game_result);
		TextViewAsserts.assertText("2", gameView, R.id.game_points);
	}
}
