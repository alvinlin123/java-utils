package alvin.concurrent;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

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
