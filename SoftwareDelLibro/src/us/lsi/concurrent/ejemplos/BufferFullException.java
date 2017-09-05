package us.lsi.concurrent.ejemplos;

public class BufferFullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BufferFullException() {
	}

	public BufferFullException(String arg0) {
		super(arg0);
	}

	public BufferFullException(Throwable arg0) {
		super(arg0);
	}

	public BufferFullException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
