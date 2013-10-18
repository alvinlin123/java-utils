/*
 * #%L
 * Java Utils
 * %%
 * Copyright (C) 2013 Alvin Lin
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.nappingcoder.jutil.http;
import static org.junit.Assert.assertEquals;


import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import com.nappingcoder.jutil.http.HttpUrlBuilder;


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
