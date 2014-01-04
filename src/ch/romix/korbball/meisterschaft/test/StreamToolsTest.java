package ch.romix.korbball.meisterschaft.test;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;
import ch.romix.korbball.meisterschaft.StreamTools;

public class StreamToolsTest extends TestCase {

	public StreamToolsTest() {
		super(StreamToolsTest.class.getPackage().getName());
	}

	public void testInputStreamToString() {
		String testString = "Hello Stream!";
		byte[] bytes = testString.getBytes();
		StringBuilder sb = StreamTools.inputStreamToString(new ByteArrayInputStream(bytes));
		assertEquals(testString, sb.toString());
	}

}
