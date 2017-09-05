package us.lsi.concurrent.ejemplos;

public class BufferEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BufferEmptyException() {
	}

	public BufferEmptyException(String message) {
		super(message);
	}

	public BufferEmptyException(Throwable cause) {
		super(cause);
	}

	public BufferEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

}
