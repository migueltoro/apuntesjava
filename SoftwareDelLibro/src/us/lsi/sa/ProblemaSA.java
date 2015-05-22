package us.lsi.sa;


/**
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema16.pdf" target="_blank">Tema16</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo del estado
 * @param <S> Tipo de la solución
 * @param <A> Tipo de las alternativas
 */
public interface ProblemaSA<E extends EstadoSA<E,S,A>,S,A> {
	/**
	 * @return El estado inicial
	 */
	E getEstadoInicial();
	
}
