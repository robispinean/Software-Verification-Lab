import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestStringProvider {

	@Test
	public void testStringContent() {
		String expected = "<html><head><title>Page under construction</title></head><body><center><b>Page under construction</b></body></html>";
		HttpStringProvider provider = new HttpStringProvider();	
		assertEquals(expected, provider.getStringForAddress(HelperClass.getUrlForPage("TestPage.html"))); 
	}

	@Test(expected = IOException.class)
	public void testException() {
		String expected = "<html><head><title>Page under construction</title></head><body><center><b>Page under construction</b></body></html>";
		HttpStringProvider provider = new HttpStringProvider();
		assertEquals(expected, provider.getStringForAddress(HelperClass.getUrlForPage("TestPage.html")));
	}
}
