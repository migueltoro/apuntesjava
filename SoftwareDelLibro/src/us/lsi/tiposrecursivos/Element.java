package us.lsi.tiposrecursivos;
import us.lsi.algoritmos.AbstractAlgoritmo;


public abstract class Element {
	/**
	 * @return Copia profunda de la expresión
	 */
	public abstract Element copy();

	/**
	 * @return Igualdad
	 */
	public abstract boolean equals(Object other);
	/**
	 * @return Igualdad
	 */
	public abstract int hashCode();
	
	protected abstract String getId();
	
	public void toDOT(String file, String titulo) {
		AbstractAlgoritmo.setFile(file);
		AbstractAlgoritmo.getFile().println("digraph "+titulo+" {  \n    size=\"100,100\"; ");	
		setFlagFalse();
		toDOT(file);
		AbstractAlgoritmo.getFile().println("}");
	}
	
	protected abstract void toDOT(String file);
	
	protected abstract void setFlagFalse();
	
	
}
