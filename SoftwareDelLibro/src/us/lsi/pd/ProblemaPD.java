package us.lsi.pd;
import java.util.*;

import us.lsi.pd.AlgoritmoPD.Sp;


/**
 * <p>Interface que debe implementar un problema para ser resuelto por la técnica de Programación Dinámica </p>
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema14.pdf" target="_blank">Tema14</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución del problema
 * @param <A> El tipo de las alternativas
 * @see AlgoritmoPD.Sp
 */
public interface ProblemaPD<S,A>  {	
	
	public enum Tipo{Min,Max,Otro}
	
	
	/**
	 * @return El tipo del problema: minimiza, maximiza o busca un valor que involucra a todas las alternativas
	 *
	 * 
	 **/
	Tipo getTipo();	
	/**
	 * @return El tamaño del problema
	 */
	int size();
	/**
	 * @return Si el problema es un caso base
	 */
	boolean esCasoBase();
	/**
	 * @pos 
	 * <ul>
	 *	<li> Si el problema no tiene solución se debe devolver null
	 *	<li> Si el problema tiene solución asociada a una alternativa esta será de la forma (a,p).Siendo a la alternativa y p la propiedad correspondiente.
	 *	<li> Si el problema tiene solución pero no asociada a una alternativa esta será de la forma (null,p).Siendo p la propiedad correspondiente.
	 *	</ul> 
	 * @return  La solución del caso base
	 */
	Sp<A> getSolucionCasoBase();
	/**
	 * @pre ls!=null, ls no está vacía, ls no contiene null, ls no contiene valores de la forma <code> (a,null) </code>
	 * 
	 * @pos 
	 * <ul>
	 *  <li> Si ls es vacía tras eliminar los valores <code> (a,null) </code> la solución devuelta es null. El problema no tiene solución.
	 *	<li> Si el problema tiene solución asociada a una alternativa, esta será de la forma <code>(a,p)</code>. Siendo <code> a </code>la alternativa y <code> p </code> la propiedad correspondiente. 
	 *	<li> Si el problema tiene solución pero no asociada a una alternativa esta será de la forma <code> (null,p)</code>. Siendo <code> p </code> la propiedad correspondiente.
	 *	</ul> 
	 * @param ls - Soluciones existentes alcanzados tras tomar las diferentes alternativas
	 * @return La solución del problema
	 */
	Sp<A> seleccionaAlternativa(List<Sp<A>> ls);
	/**
	 * @pre 0 &lt; = i &lt; getNumeroSubProblemas(a)
	 * @param a - Alternativa escogida
	 * @param np - Número del subproblema
	 * @return Subproblema
	 */
	ProblemaPD<S,A> getSubProblema(A a, int np);
	/**
	 * @pre <code> ls.size() == getNumeroSubProblemas(a) </code>, a está incluida en <code> getAlternativas() </code> y
	 * ls no contiene null
	 * @param a - Alternativa escogida
	 * @param ls - Soluciones de los subproblemas
	 * @return La solución del problema alcanzado siguiendo la alternativa <code> a </code> 
	 * y las soluciones parciales de los subproblemas del mismo están en ls 
	 */
	Sp<A> combinaSolucionesParciales(A a , List<Sp<A>> ls);	
	/**
	 * @pos Si un problema no tiene solución el conjunto de alternativas es vacío 
	 * @return Las alternativas disponibles para el problema
	 */
	List<A> getAlternativas();
	/**
	 * @param a - Alternativa escogida
	 * @return Número de subproblemas para una alternativa dada
	 */
	int getNumeroSubProblemas(A a);
	/**
	 * @pre El problema es un caso base
	 * @pos 
	 * <ul>
	 *  <li> Si sp es de la forma <code> (a,p) </code> la solución se reconstruye a partir de esos dos valores. 
	 *  <li> Si es de la forma <code> (null,p) </code> sólo a partir de <code> p </code>.
	 * </ul> 
	 * @param sp - Solución parcial del caso base
	 * @return La solución para un caso base si la solución parcial es sp. 
	 */
	S getSolucionReconstruida(Sp<A> sp);	
	/**
	 * @pre 
	 * <p> El problema no es un caso base, <code> ls !=null </code>, <code> ls.size() &gt; 0 </code> y ls no contiene null.</p>
	 * <p> Si el tipo es Min o Max entonces sp = (a,p) </p>
	 * <p> Si tipo es Todas entonces sp puede ser (a,p) o (null,p). Entonces  ls tiene en cada caso el significado correspondiente </p>
	 * @pos
	 * <ul>
	 *  <li> Si <code> sp = (a,p) </code> la solución se reconstruye a partir de <code> a, p, ls </code> estando <code> ls </code> las 
	 *  de los subproblemas del problema que se alcanza siguiendo la alternativa <code> a </code> 
	 *  <li> Si <code> sp = (null,p) </code> la solución se reconstruye a partir de las 
	 *  soluciones de los problemas que tienen solución y se alcanzan siguiendo las diferentes alternativas posibles.
	 * </ul> 
	 * @param sp - Solución parcial del problema
	 * @param ls - 
	 * <ul>
	 *  <li> Si <code> sp = (a,p)  </code> la solución de los subproblemas del problema que se alcanza 
	 *  siguiendo la alternativa <code> a </code>. 
	 *  <li> Si <code> sp = (null,p) </code>  las soluciones de los problemas que tienen solución y 
	 *  se alcanzan siguiendo las diferentes alternativas posibles. 
	 * </ul>
	 * Soluciones obtenidas siguiendo la diferentes alternativas
	 * @return La solución para el problema. 
	 */
	S getSolucionReconstruida(Sp<A> sp, List<S> ls);	
	
	/**
	 * @pos 
	 * <ul>
	 * <li> Si el problema es de minimización el valor debe ser 
	 * una cota inferior del valor de la propiedad objetivo del problema inicial, 
	 * asumiendo que se ha alcanzado el problema actual y se  va a seguir la alternativa <code> a </code>.
	 * La alternativa puede ser descartada si <code> getObjetivoEstimado(a) &gt; =  AlgoritmoPD.getMejorValor() </code>,
	 * puesto que el valor objetivo de la solución obtnida estaría por encima del mejor valor obtenido hasta el momento.
	 * Es decir nos quedamos con las alternativas que cumplen 
	 * <code> getObjetivoEstimado(a) &lt;   AlgoritmoPD.getMejorValor() </code>.
	 * Donde <code> AlgoritmoPD.getMejorValor() </code> es el mejor valor encontrado por el algoritmo hasta ahora.
	 * <li> Si el problema original fuera de maximización el valor debe ser 
	 * una cota superior del valor de la propiedad objetivo del problema inicial, 
	 * asumiendo que se ha alcanzado el problema actual y se  va a seguir la alternativa <code> a </code>.
	 *  Es decir nos quedamos con las alternativas que cumplen 
	 * <code> getObjetivoEstimado(a) &gt;   AlgoritmoPD.getMejorValor() </code>.
	 * </ul>
	 * <p> Si no es posible estimar esa cota entonces devolverá MIN_VALUE si el problema es de minimización
	 *  y MAX_VALUE si es de maximización. Estos son los valores por defecto. </p>
	 * @param a - Alternativa elegida
	 * @return El valor objetivo estimado si seguimos <code> a </code>
	 */
	Double getObjetivoEstimado(A a);
	/**
	 * Se asume que se llama al método sólo cuando se ha resuelto el problema y por lo tanto sabemos el valor de la propiedad solución.
	 * En muchos casos se puede calcular sumando un valor acumulado al valor conocido de la solución.
	 * @pos Si no es posible calcularla  se devolverá MAX_VALUE si el problema es de minimización y MIN_VALUE si es de maximización. Estos son los valores por defecto.
	 * @return Valor de propiedad objetivo del problema inicial asumiendo que estamos en el problema actual   
	 */
	Double getObjetivo();
}
