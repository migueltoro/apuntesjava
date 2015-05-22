package us.lsi.ag;

import java.util.List;

public interface Cromosoma<T> {
	/**
	 * @return La secuencia decodificada según la información en el cromosoma
	 */
	List<T> decode();
	
	
	/**
	 * @return Fitness del cromosoma
	 */
	double fitness();
		
}
