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

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import jutil.concurrent.ProactiveFutureTask;

import org.junit.Test;

public class ProactiveFutureTaskTest {
	
	@Test
	public void testCallbackGetCalled() throws Exception{
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		final AtomicBoolean callbackCalled = new AtomicBoolean(false);
		ProactiveFutureTask.Callback<Object> callback = 
				new ProactiveFutureTask.Callback<Object>() {

					@Override
					public void executionFinished(ProactiveFutureTask<Object> task) {

						callbackCalled.compareAndSet(false, true);
					}	
					
				};
		
		ProactiveFutureTask<Object> task = new ProactiveFutureTask<Object>(r, new Object(), callback );
		Thread t = new Thread(task);
		t.start();
		t.join();
		
		assertEquals(true, callbackCalled.get());
	}

	@Test
	public void testNullCallbackWillNotCauseException() throws Exception{
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	
		
		ProactiveFutureTask<Object> task = new ProactiveFutureTask<Object>(r, new Object());
		Thread t = new Thread(task);
		t.start();
		
		try {
			task.get();
		} catch (ExecutionException e) {
			fail();
		}
	}
	
	@Test
	public void testCallbackGetCalledEvenIfException() throws Exception{
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				
				throw new RuntimeException();
			}
		};
		
		final AtomicBoolean callbackCalled = new AtomicBoolean(false);
		ProactiveFutureTask.Callback<Object> callback = 
				new ProactiveFutureTask.Callback<Object>() {

					@Override
					public void executionFinished(ProactiveFutureTask<Object> task) {

						callbackCalled.compareAndSet(false, true);
					}	
					
				};
		
		ProactiveFutureTask<Object> task = new ProactiveFutureTask<Object>(r, new Object(), callback );
		Thread t = new Thread(task);
		t.start();
		t.join();
		
		assertEquals(true, callbackCalled.get());
	}

}
