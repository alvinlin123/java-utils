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
package jutil.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Extension of {@link FutureTask} that will call the given 
 * call back upon end of execution (either normally or abnormally).
 * 
 * @author chlin
 *
 * @param <V>
 */
public class ProactiveFutureTask<V> extends FutureTask<V> {

	private Callback<V> _callback;
	
	public ProactiveFutureTask(Runnable runnable, V result) {
		super (runnable, result);
	}
	
	public ProactiveFutureTask(Runnable r, V result, Callback<V> callback) {
		
		super(r, result);
		_callback = callback;
	}
	
	public ProactiveFutureTask(Callable<V> c){
		
		super(c);
	}
	
	public ProactiveFutureTask(Callable<V> c, Callback<V> callback) {
		super(c);
		_callback = callback;
	}
	
	@Override
	protected void done() {
		
		super.done();
		
		if (_callback != null) {
			_callback.executionFinished(this);
		}
	}

	public interface Callback<V> {
		
		public void executionFinished(ProactiveFutureTask<V> task);
	}
}
