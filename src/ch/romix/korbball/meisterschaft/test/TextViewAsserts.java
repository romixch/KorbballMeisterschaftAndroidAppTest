package ch.romix.korbball.meisterschaft.test;

import junit.framework.Assert;
import android.view.View;
import android.widget.TextView;

public class TextViewAsserts {

	public static void assertText(String expectedText, View view, int viewId) {
		TextView dayField = (TextView) view.findViewById(viewId);
		Assert.assertEquals(expectedText, dayField.getText().toString());
	}
}
