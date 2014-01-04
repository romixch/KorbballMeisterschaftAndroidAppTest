package ch.romix.korbball.meisterschaft.test;

import junit.framework.Assert;
import android.content.Context;
import android.test.AndroidTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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
		assertTextViewText(day + ":", dayView, R.id.game_day);
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
		assertTextViewText(game.getHall(), gameView, R.id.game_hall);
		assertTextViewText(game.getRound(), gameView, R.id.game_round);
		assertTextViewText(game.getTeamA() + " - " + game.getTeamB(), gameView, R.id.game_teams);
		assertTextViewText(game.getTime(), gameView, R.id.game_time);
		assertTextViewText(game.getResultA() + " : " + game.getResultB(), gameView, R.id.game_result);
		assertTextViewText("2", gameView, R.id.game_points);
	}

	private void assertTextViewText(String expectedText, View view, int viewId) {
		TextView dayField = (TextView) view.findViewById(viewId);
		Assert.assertEquals(expectedText, dayField.getText().toString());
	}
}
