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

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.net.URLCodec;

/**
 * This builder is stingy, it will not include character that is
 * not required. For example, instead of http://www.ea.com/ this
 * builder will produce http://www.ea.com.
 * 
 * 
 * @author alvinlin
 *
 */
public final class HttpUrlBuilder {

	private static final String CHAR_SET = "UTF-8";
	
	private StringBuilder _query = new StringBuilder();
	private StringBuilder _path = new StringBuilder();
	private URLCodec codec = new URLCodec();
	private String _protocol;
	private String _host = "";
	private int _port;
	
	public static HttpUrlBuilder httpBuilder() {
		
		return new HttpUrlBuilder(false);
	}
	
	public static HttpUrlBuilder httpSecureBuilder() {
		return new HttpUrlBuilder(true);
	}
	
	private HttpUrlBuilder(boolean https) {
		
		if (https) {
			_protocol = "https";
		} else {
			_protocol = "http";
		}
		
	}
	
	/**
	 * Set the host.
	 * 
	 * @param host
	 * @return
	 * @throws HttpUrlBuilderException
	 */
	public HttpUrlBuilder host(String host)
	throws HttpUrlBuilderException {
		
		if (host == null) {
			throw new HttpUrlBuilderException("given host is null");
		}
		
		_host = host;
		
		return this;
	}
	
	/**
	 * Set the port.
	 * 
	 * @param port
	 * @return
	 * @throws HttpUrlBuilderException
	 */
	public HttpUrlBuilder port(int port)
	throws HttpUrlBuilderException {
		
		if (port <= 0 || port > 65535) {
			throw new HttpUrlBuilderException(port + " is not a valid port");
		}
		
		_port = port;
		
		return this;
	}
	
	/**
	 * Set the path.
	 * 
	 * @param path
	 * @return
	 * @throws HttpUrlBuilderException
	 */
	public HttpUrlBuilder path(String path)
	throws HttpUrlBuilderException {
		
		if (path == null) {
			throw new HttpUrlBuilderException("given path is null");
		}
		
		_path.setLength(0);
		String[] elements = path.split("/");
		
		for (String elt : elements) {
			if (elt.isEmpty()) {
				continue;
			}
			
			try {
				_path.append(codec.encode(elt, CHAR_SET)).append("/");
			} catch (UnsupportedEncodingException e) {
				throw new HttpUrlBuilderException("unsupported char set (you really shouldn't see this though..)", e);
			}
		}
		
		return this;
	}
	
	public HttpUrlBuilder addPathElement(String element)
			throws HttpUrlBuilderException {
		
		try {
			_path.append(codec.encode(element, CHAR_SET)).append("/");
		} catch (UnsupportedEncodingException e) {
			throw new HttpUrlBuilderException("unsupported char set (you really shouldn't see this though..)", e);
		}
		
		return this;
	}
	
	/**
	 * Add a key value pair to the query string. 
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @throws HttpUrlBuilderException
	 */
	public HttpUrlBuilder addQuery(String name, String value)
	throws HttpUrlBuilderException {
		
		try {
			_query.append(codec.encode(name, CHAR_SET)).append("=").append(codec.encode(value, CHAR_SET)).append("&");
		} catch (UnsupportedEncodingException e) {
			//really shouln't get here though....
			throw new HttpUrlBuilderException("unsupported char set (you really shouldn't see this though...)", e);
		}
		
		return this;
	}
	
	/**
	 * Add bunch of key value pairs in the given map as query string. 
	 * 
	 * @param nvPair
	 * @return
	 * @throws HttpUrlBuilderException
	 */
	public HttpUrlBuilder addQuery(Map<String, String> nvPair)
	throws HttpUrlBuilderException {
		
		if (nvPair == null) {
			return this;
		}
		
		Set<String> names = nvPair.keySet();
		for (String n : names) {
			String v = nvPair.get(n);
			if (v == null) {
				v = "";
			}
			addQuery(n, v);
		}
		
		return this;
	}
	
	/**
	 *
	 * @return
	 * @throws IllegalStateException
	 */
	public String toUrlString() {
		
		if (_host==null) {
			throw new IllegalArgumentException("host must be set ");
		}
		
		return toString();
	}
	
	/**
	 *
	 * @return
	 * @throws IllegalStateException
	 */
	public String toString() {
		
		StringBuilder url = new StringBuilder();
		
		url
		.append(_protocol).append("://").append(_host);
		
		if (_port > 0) {
			url.append(":"+_port);
		}
		
		if (_path.length() > 0) {

			if (_path.length() > 0 && _path.charAt(_path.length() - 1) == '/') {
				_path.setLength(_path.length() - 1);
			}
			
			url.append("/").append(_path);
		}
		
		if (_query.length() > 0) {
			url.append("?").append(_query);
		}
		
		if (url.charAt(url.length() - 1) == '&') {
			url.setLength(url.length() - 1);
		}
		
		return url.toString();
	}
}
