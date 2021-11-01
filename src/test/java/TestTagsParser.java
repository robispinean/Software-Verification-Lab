import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class TestTagsParser {
	@Test
	public void testCounter() {
		TagsParser parser = new TagsParser(HelperClass.getUrlForPage("TestPage.html"));
		assertEquals(6, parser.getTagsCount());
	}

	@Test
	public void testZeroTag() {
		TagsParser parser = new TagsParser(HelperClass.getUrlForPage("ZeroTag.html")); 
		assertEquals(0, parser.getTagsCount());
	} 

	@Test
	public void testTwoTag() {
		TagsParser parser = new TagsParser(HelperClass.getUrlForPage("TwoTags.html"));
		assertEquals(2, parser.getTagsCount());
	}

	@Test
	public void testEmptyTag() {
		TagsParser parser = new TagsParser(HelperClass.getUrlForPage("Empty.html"));
		assertEquals(0, parser.getTagsCount());
	}

	@Test
	public void testGetTagsCount(){
		TagsParser parser = new TagsParser(HelperClass.getUrlForPage("TestPage.html")) ;
		assertEquals("head", parser.getTagAt(2));
	}

	@Test
	public void testCounterWithAnonimousWithMock() {
		String returnValue="<b></b><b></b>";
		HttpStringProvider provider = mock(HttpStringProvider.class);
		when(provider.getStringForAddress(anyString())).thenReturn(returnValue);
		
		TagsParser counter = new TagsParser(returnValue);
		counter.setProvider(provider);
		
		assertEquals(2, counter.getTagsCount());
		verify(provider).getStringForAddress(anyString());
	}
	
	@Test
	public void testCounterWithAnonimousWithMock2() {
		HttpStringProvider provider = mock(HttpStringProvider.class);
		when(provider.getStringForAddress(anyString())).thenReturn("<b></b><b></b>");
		
		TagsParser counter = new TagsParser("http://www.loose.upt.ro/vvswiki");
		counter.setProvider(provider);
		
		assertEquals(2, counter.getTagsCount());
		verify(provider).getStringForAddress(anyString());
	}
	
	
	@Test
	public void testCounterWithAnonimous() {
		TagsParser counter = new TagsParser("http://www.loose.upt.ro/vvswiki") ;
		counter.setProvider(new HttpStringProvider(){
			@Override
			public String getStringForAddress(String urlAddress) {
				return "";
			}
		});
		assertEquals(0, counter.getTagsCount());
	}


	@Test
	public void testCounterOneTagWithAnonimous() {
		TagsParser counter = new TagsParser("http://www.loose.upt.ro/vvswiki");
		counter.setProvider(new HttpStringProvider(){
			@Override
			public String getStringForAddress(String url) {
				return "<html></html>";
			}
		});

		assertEquals(1, counter.getTagsCount());
	}

	@Test
	public void testCounterTwoTagWithAnonimous() {
		TagsParser counter = new TagsParser("http://www.loose.upt.ro/vvswiki");
		counter.setProvider(new HttpStringProvider(){
			@Override
			public String getStringForAddress(String url) {
				return "<html><body>Test</body></html>";
			}
		});

		assertEquals(2, counter.getTagsCount());
	}
	
	@Test
	public void testCounterTwoTagWithAnonimousWithMock() {
		HttpStringProvider provider = mock(HttpStringProvider.class);
		when(provider.getStringForAddress(anyString())).thenReturn("<html><body>Test</body></html>");
		
		
		TagsParser counter = new TagsParser("http://www.loose.upt.ro/vvswiki");
		counter.setProvider(provider);

		assertEquals(2, counter.getTagsCount());
		verify(provider, times(1)).getStringForAddress(anyString());
	}


	@Test
	public void testCounterWithAnonimousGeneric() {
		TagsParser counter = new TagsParser("<b><b></b>") ;
		counter.setProvider(new GenericHttpProviderMock());
		assertEquals(2, counter.getTagsCount());
	}

	private static class GenericHttpProviderMock extends HttpStringProvider {
		@Override
		public String getStringForAddress(String u) {
			return u;
		}
	}
	
}
