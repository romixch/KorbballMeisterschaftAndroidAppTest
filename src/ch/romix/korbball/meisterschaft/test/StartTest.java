package ch.romix.korbball.meisterschaft.test;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import ch.romix.korbball.meisterschaft.Info;
import ch.romix.korbball.meisterschaft.R;
import ch.romix.korbball.meisterschaft.RankingActivity;
import ch.romix.korbball.meisterschaft.Start;

public class StartTest extends ActivityInstrumentationTestCase2<Start> {
	private ActivityMonitor activityMonitor;

	public StartTest() {
		super(Start.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		getActivity();
	}

	@Override
	protected void tearDown() throws Exception {
		if (activityMonitor != null) {
			getInstrumentation().removeMonitor(activityMonitor);
			activityMonitor = null;
		}
		super.tearDown();
	}

	public void testIfIntentFilterMatchesBrowserViewIntent() throws Exception {
		IntentFilter intentFilter = createIntentFilterToMatchOpenPollInBrowser();
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://romixch.typeform.com/to/r3ELGk"));
		int match = intentFilter.match(null, intent, false, "");
		assertEquals(IntentFilter.MATCH_CATEGORY_SCHEME + IntentFilter.MATCH_ADJUSTMENT_NORMAL, match);
	}

	private IntentFilter createIntentFilterToMatchOpenPollInBrowser() {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_VIEW);
		intentFilter.addDataScheme("https");
		return intentFilter;
	}

	public void testIfIntentFilterMatchesInfoActivity() throws Exception {
		IntentFilter intentFilter = createIntentFilterToMatchInfoActivity();
		Intent intent = new Intent(getActivity(), Info.class);
		int match = intentFilter.match(null, intent, false, "");
		assertEquals(IntentFilter.MATCH_CATEGORY_EMPTY + IntentFilter.MATCH_ADJUSTMENT_NORMAL, match);
	}

	private IntentFilter createIntentFilterToMatchInfoActivity() {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MAIN);
		intentFilter.addDataPath("ch.romix.korbball.meisterschaft.Info", 0);
		return intentFilter;
	}

	public void testIfIntentFilterMatchesRankingActivity() {
		IntentFilter filter = createIntentFilterToMatchRankingActivity();
		Intent intent = new Intent(getActivity(), RankingActivity.class);
		intent.putExtra(RankingActivity.INTENT_GROUP_ID, "1");
		intent.putExtra(RankingActivity.INTENT_GROUP_NAME, "Herren 1. Liga");
		int match = filter.match(null, intent, false, "");
		assertEquals(IntentFilter.MATCH_CATEGORY_EMPTY + IntentFilter.MATCH_ADJUSTMENT_NORMAL, match);
	}

	private IntentFilter createIntentFilterToMatchRankingActivity() {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MAIN);
		intentFilter.addDataPath("ch.romix.korbball.meisterschaft.RankingActivity", 0);
		return intentFilter;
	}

	public void testIfNextFeatureButtonIsPresent() {
		Button nextFeatureButton = getNextFeatureButton();
		assertEquals("Bestimme das nächste Feature dieser App!", nextFeatureButton.getText());
	}

	@UiThreadTest
	public void testIfNextFeatureClickOpensPollInBrowser() throws Exception {
		IntentFilter browserFilter = createIntentFilterToMatchOpenPollInBrowser();
		activityMonitor = getInstrumentation().addMonitor(browserFilter, null, true);
		clickNextFeatureButton();
		Thread.sleep(2000); // have no better idea how to wait for ui
		assertEquals(1, activityMonitor.getHits());
	}

	private void clickNextFeatureButton() {
		final Button nextFeatureButton = getNextFeatureButton();
		nextFeatureButton.performClick();
	}

	private Button getNextFeatureButton() {
		return (Button) getActivity().findViewById(R.id.nextFeature);
	}

	public void testIfInfoOptionOpensInfoActivity() throws Exception {
		IntentFilter infoActivityFilter = createIntentFilterToMatchInfoActivity();
		activityMonitor = getInstrumentation().addMonitor(infoActivityFilter, null, false);
		clickInfoOption();
		assertThatActivityIsOpened();
	}

	private void assertThatActivityIsOpened() {
		Activity infoActivity = activityMonitor.waitForActivityWithTimeout(2000);
		infoActivity.finish();
		assertEquals(1, activityMonitor.getHits());
	}

	private void clickInfoOption() {
		final Start activity = getActivity();
		getInstrumentation().invokeMenuActionSync(activity, R.id.menu_info, 0);
	}

	public void testGetGroups() throws Exception {
		IntentFilter rankingActivityFilter = createIntentFilterToMatchRankingActivity();
		activityMonitor = getInstrumentation().addMonitor(rankingActivityFilter, null, false);
		Start startActivity = getActivity();
		startActivity.waitForGroups();
		final ListView listView = (ListView) startActivity.findViewById(R.id.listLeague);
		final View view = listView.getAdapter().getView(1, null, null);
		startActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listView.performItemClick(view, 0, 0);
			}
		});
		assertThatActivityIsOpened();
	}
}
