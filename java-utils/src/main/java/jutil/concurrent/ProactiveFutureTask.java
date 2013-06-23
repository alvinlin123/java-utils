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
