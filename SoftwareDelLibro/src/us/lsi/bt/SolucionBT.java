package us.lsi.bt;

public interface SolucionBT extends Comparable<SolucionBT>{
	
	/**
	 * @return La propiedad de la solución con respecto a la cual vamos 
	 * a definir el criterio de optimización. La soluciones se comparan por esta propiedad
	 */
	Double getObjetivo();
	
	default public int compareTo(SolucionBT sl) {
		return this.getObjetivo().compareTo(sl.getObjetivo());
	}
	

}
