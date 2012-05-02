package http.url.utils;
import static org.junit.Assert.assertEquals;

import http.url.utils.HttpUrlBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;


public class HttpUrlBuilderTest {

	@Test
	public void testNoQueryParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.google.com");
		
		assertEquals("http://www.google.com", builder.toUrlString());
	}
	
	@Test
	public void testHttpsNoQueryParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpSecureBuilder().host("www.google.com");
		
		assertEquals("https://www.google.com", builder.toUrlString());
	}
	
	@Test
	public void testOneQueryParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder.addQuery("type", "origin");
		
		assertEquals("http://www.ea.com?type=origin", builder.toString());
	}
	
	@Test
	public void testHttpsOneQueryParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpSecureBuilder().host("www.ea.com");
		builder.addQuery("type", "origin");
		
		assertEquals("https://www.ea.com?type=origin", builder.toString());
	}
	
	@Test
	public void testMultiQueryParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder
			.addQuery("type", "origin")
			.addQuery("hello","world");
		
		assertEquals("http://www.ea.com?type=origin&hello=world", builder.toString());
	}
	
	@Test
	public void testHttpsMultiQueryParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpSecureBuilder().host("www.ea.com");
		builder
			.addQuery("type", "origin")
			.addQuery("hello","world");
		
		assertEquals("https://www.ea.com?type=origin&hello=world", builder.toString());
	}
	
	@Test
	public void testQueryWithSpaceParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder
			.addQuery("type", "origin store")
			.addQuery("hello","world");
		
		assertEquals("http://www.ea.com?type=origin+store&hello=world", builder.toString());
	}
	
	@Test
	public void testHttpsQueryWithSpaceParam() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpSecureBuilder().host("www.ea.com");
		builder
			.addQuery("type", "origin store")
			.addQuery("hello","world");
		
		assertEquals("https://www.ea.com?type=origin+store&hello=world", builder.toString());
	}
	
	@Test
	public void testAddMultiQueryAtOnece() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		
		//use linked map to preserve order
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("type", "origin");
		params.put("hello", "world");
		
		builder.addQuery(params);
		
		assertEquals("http://www.ea.com?type=origin&hello=world", builder.toString());
	}
	
	@Test
	public void testHttpsAddMultiQueryAtOnece() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpSecureBuilder().host("www.ea.com");
		
		//use linked map to preserve order
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("type", "origin");
		params.put("hello", "world");
		
		builder.addQuery(params);
		
		assertEquals("https://www.ea.com?type=origin&hello=world", builder.toString());
	}
	
	//Ok enough test of https, no more below.
	
	@Test
	public void testAddPathSimple() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder.path("/need-for-speed-world");
		
		assertEquals("http://www.ea.com/need-for-speed-world", builder.toString());
		
		builder.path("need-for-speed-world");
		assertEquals("http://www.ea.com/need-for-speed-world", builder.toString());
	}
	
	@Test
	public void testAddPathComplex() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder.path("/games/nfs/need-for-speed-world");
		
		assertEquals("http://www.ea.com/games/nfs/need-for-speed-world", builder.toString());
		
		builder.path("games/nfs/need-for-speed-world");
		assertEquals("http://www.ea.com/games/nfs/need-for-speed-world", builder.toString());
		
		builder.path("games/nfs/need-for-speed-world/");
		assertEquals("http://www.ea.com/games/nfs/need-for-speed-world", builder.toString());
	}
	
	@Test
	public void testAddPathComplexWithWhiteSpace() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder.path("/  games/nfs/need-for-speed-world");
		
		assertEquals("http://www.ea.com/++games/nfs/need-for-speed-world", builder.toString());
		
		builder.path("  games/nfs/need-for-speed-world");
		assertEquals("http://www.ea.com/++games/nfs/need-for-speed-world", builder.toString());
	}
	
	@Test
	public void testAddPathElement() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder.addPathElement("games");
		builder.addPathElement("need-for-speed");
		
		assertEquals("http://www.ea.com/games/need-for-speed", builder.toString());
	}
	
	@Test
	public void testAddPathElementWithSlash() throws Exception {
		HttpUrlBuilder builder = HttpUrlBuilder.httpBuilder().host("www.ea.com");
		builder.addPathElement("games");
		builder.addPathElement("need-for-/speed");
		
		assertEquals("http://www.ea.com/games/need-for-%2Fspeed", builder.toString());
	}
}
