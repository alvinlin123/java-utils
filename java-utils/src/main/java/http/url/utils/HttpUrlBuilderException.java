package http.url.utils;

public class HttpUrlBuilderException extends Exception {
	
	private static final long serialVersionUID = 2149753087953456423L;

	public HttpUrlBuilderException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public HttpUrlBuilderException(String msg) {
		super(msg);
	}
}
