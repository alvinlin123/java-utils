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

public class HttpUrlBuilderException extends Exception {
	
	private static final long serialVersionUID = 2149753087953456423L;

	public HttpUrlBuilderException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public HttpUrlBuilderException(String msg) {
		super(msg);
	}
}
