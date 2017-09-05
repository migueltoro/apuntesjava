package us.lsi.voraz;

import us.lsi.algoritmos.AbstractAlgoritmo;

/**
 * Implementación de un Algoritmo Voraz. Un problema que se quiera resolver por
 * este algortimo debe implementar el interface ProblemaVZ &lt; E,S,A &gt;
 * 
 * Para usar esta técnica hay que considerar un conjunto de estados y unas
 * alternativas para pasar de unos a otros. 
 * 
 * 
 * @param <E> Tipo del estado
 * @param <S> Tipo de la solución
 * @param <A> Tipo de la alternativa
 */
public class AlgoritmoVZ<E extends EstadoVZ<E, S, A>, S, A> extends AbstractAlgoritmo {

	private ProblemaVZ<E, S, A> problema;
	private E estado;
	/**
	 * Solución encontrada
	 */
	public S solucion = null;
	


	/**
	 * @param p Problema a resolver
	 */
	public AlgoritmoVZ(ProblemaVZ<E, S, A> p) {
		problema = p;
	}



	public S getSolucion() {
		return solucion;
	}



	/**
	 * Ejecución del algoritmo
	 */
	public void ejecuta() {

		estado = problema.getEstadoInicial();
		while (!estado.condicionDeParada()) {

			A a = estado.getAlternativa();

			estado =  estado.next(a);
		}
		solucion = estado.getSolucion();


	}


}

