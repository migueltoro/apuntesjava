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
	 * @return Si el problema está fuera de rango y su solución es null
	 */
	default boolean estaFueraDeRango(){
		return false;
	}
	/**
	 * @return Si el problema es un caso base
	 * 
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
	Sp<A> getSolucionParcialCasoBase();
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
	Sp<A> getSolucionParcial(List<Sp<A>> ls);
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
	Sp<A> getSolucionParcialPorAlternativa(A a , List<Sp<A>> ls);	
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
	S getSolucionReconstruidaCasoBase(Sp<A> sp);	
	/**
	 * @pre 
	 * <p> El problema no es un caso base, <code> ls !=null </code>, <code> ls.size() &gt; 0 </code> y ls no contiene null.</p>
	 * <p> Si el tipo es Min o Max entonces sp = (a,p) </p>
	 * <p> Si tipo es Otro entonces sp es (null,p) y S debe ser Double. </p>
	 * @pos
	 * <ul>
	 *  <li> Si <code> sp = (a,p) </code> la solución se reconstruye a partir de <code> a, p, ls </code> estando en <code> ls </code> las 
	 *  soluciones de los subproblemas que se alcanzan siguiendo la alternativa <code> sp.alternativa </code> 
	 *  <li> Si <code> sp = (null,p) </code> la solución es igual a <code> sp.propiedad </code>.
	 * </ul> 
	 * @param sp - Solución parcial del problema
	 * @param ls - 
	 * <ul>
	 *  <li> Si <code> sp = (a,p)  </code> La solución de los subproblemas que se alcanzan 
	 *  siguiendo la alternativa <code> sp.alternativa </code>. 
	 *  <li> Si <code> sp = (null,p) </code> entonces <code> ls </code> es irrelevante. 
	 * </ul>
	 * @return La solución para el problema. 
	 */
	S getSolucionReconstruidaCasoRecursivo(Sp<A> sp, List<S> ls);	
	
	/**
	 * @pre El uso del método es relevante si usamos la técnica con filtro. Si no la usamos el método es irrelevante.
	 * @pos 
	 * <ul>
	 * <li> Si el problema es de minimización el valor debe ser 
	 * una cota inferior del valor de la propiedad objetivo del problema inicial, 
	 * asumiendo que estamos en el problema actual y se  va a seguir la alternativa <code> a </code>.
	 * La alternativa puede ser descartada si <code> getObjetivoEstimado(a) &gt; =  AlgoritmoPD.getMejorValor() </code>,
	 * puesto que el valor objetivo de la solución obtenida estaría por encima del mejor valor obtenido hasta el momento.
	 * Es decir nos quedamos con las alternativas que cumplen 
	 * <code> getObjetivoEstimado(a)() &lt;   AlgoritmoPD.getMejorValor() </code>.
	 * Donde <code> AlgoritmoPD.getMejorValor() </code> es el mejor valor encontrado por el algoritmo hasta ahora.
	 * <li> Si el problema original fuera de maximización el valor debe ser 
	 * una cota superior del valor de la propiedad objetivo del problema actual, 
	 * asumiendo que estamos en el problema actual y se  va a seguir la alternativa <code> a </code>.
	 *  Es decir nos quedamos con las alternativas que cumplen 
	 * <code> getObjetivoEstimado(a) &gt;   AlgoritmoPD.getMejorValor() </code>.
	 * </ul>
	 * @param a - Alternativa elegida
	 * @return Una cota para valor objetivo si seguimos <code> a </code>
	 */
	default Double getObjetivoEstimado(A a){
		return null;
	}
	/**
	 * 
	 * @pre El uso del método es relevante si usamos la técnica con filtro. Si no la usamos el método es irrelevante.
	 * @return Valor de la propiedad objetivo del problema inicial si es posible calcularlo asumiendo
	 * que estamos en el problema actual. Si no es posible null.
	 */
	default Double getObjetivo() {
		return null;
	}
	
}
