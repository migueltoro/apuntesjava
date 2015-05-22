package us.lsi.flowgraph;

/**
 * Excepción que se dispara si no se encuentra solucíon al resolver 
 * una Red de Flujo
 * 
 * @author Miguel Toro
 *
 */
public class NoSeEncuentraSolucion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSeEncuentraSolucion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoSeEncuentraSolucion(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NoSeEncuentraSolucion(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NoSeEncuentraSolucion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NoSeEncuentraSolucion(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
