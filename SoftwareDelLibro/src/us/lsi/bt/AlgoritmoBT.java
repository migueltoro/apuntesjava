package us.lsi.bt;


import java.util.List;
import java.util.Set;


import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Lists2;
import us.lsi.bt.ProblemaBT.Tipo;

import com.google.common.collect.*;






/**
 * <p> Algoritmo que implementa la técnica de Bactracking con sus variantes. 
 * Un problema que se quiera resolver con esta técnica debe implementar el interface ProblemaBT &lt; S,A &gt;</p>
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema15.pdf" target="_blank">Tema15</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución
 * @param <A> El tipo de la alternativa
 */
public class AlgoritmoBT<S, A> extends AbstractAlgoritmo {
	
	
	/**
	 * Solución obtenida si es única
	 */
    public S solucion = null;
    /**
	 * Conjunto de soluciones encontradas
	 */
	public Set<S> soluciones; 
	/**
	 * Número de soluciones que se buscan
	 */
	public static int numeroDeSoluciones = 1;
	/**
	 * Si se quiere aplicar la técnica aleatoria para escoger una de las alternativas
	 */
	public static boolean isRandomize = false;
	/**
	 * Tamaño umbral a partir del cual se escoge aleatoriamente una de las alternativas
	 */
	public static Integer sizeRef = 10;
	/**
	 * Si solo se busca una solución
	 */
	public static boolean soloLaPrimeraSolucion = true;
	
	private static Double mejorValor = Double .MAX_VALUE;
	
	private static Tipo tipo;

    
   
	
	private ProblemaBT<S,A> problema; 
    private EstadoBT<S,A> estado;
	private boolean exito = false;
	
	
	
	 private static boolean isMin(){
	    	return tipo.equals(Tipo.Min);
	 }
	    
	 private static boolean isMax(){
	    	return tipo.equals(Tipo.Max);
	 }
	
	 private static boolean isTodas(){
	    	return tipo.equals(Tipo.Otro);
	 }
	
	/**
	 * @return El mejor valor de la propiedade objetivo del problema inicial encontrado por el algoritmo hasta este momento
	 */
	public static Double getMejorValor() {
		return mejorValor;
	}

	/**
	 * @param p - El problema a resolver
	 */
	public AlgoritmoBT(ProblemaBT<S,A> p){
    	problema = p; 
    }
    
    /**
     * Ejecución del algoritmo
     */
	public void ejecuta() {
		tipo = problema.getTipo();
	    mejorValor = isMin()? Double.MAX_VALUE: Double.MIN_VALUE;  
    	soluciones =  Sets.newHashSet(); 	
		do {
			estado = problema.getEstadoInicial();
			bt();
		} while(isRandomize && soluciones.size()<AlgoritmoBT.numeroDeSoluciones);
	}

    private Iterable<A> filtraRandomize(EstadoBT<S,A> p, Iterable<A> alternativas){
    	Iterable<A> alt;
		if(isRandomize && p.size()>sizeRef){
			List<A> ls = Lists.newArrayList(alternativas);
			alt = Lists2.randomUnitary(ls);
		}else{
			alt = alternativas;
		}
		return alt;
	}
    
	private void actualizaSoluciones() {
		Double objetivo = estado.getObjetivo();
		S s = estado.getSolucion();
		if (s!= null && (isMin() && objetivo < AlgoritmoBT.mejorValor || isMax() && objetivo > AlgoritmoBT.mejorValor || isTodas())) {
			solucion = s;
			soluciones.add(solucion);
			AlgoritmoBT.mejorValor = objetivo;
		}
	}
    
    private void bt() {	
    	if(estado.isFinal()){    		
    		actualizaSoluciones();
    		if(AlgoritmoBT.soloLaPrimeraSolucion  && solucion!=null) exito = true;
    		if(!AlgoritmoBT.soloLaPrimeraSolucion  && soluciones.size()>=AlgoritmoBT.numeroDeSoluciones) exito = true;
    	} else {
    			for(A a: filtraRandomize(estado,estado.getAlternativas())){  
    					if(isMin() && estado.getObjetivoEstimado(a) >= mejorValor) continue;
    					if(isMax() && estado.getObjetivoEstimado(a) <= mejorValor) continue;
    					estado.avanza(a); 
    					bt();  
    					estado.retrocede(a); 
    					if (exito) break;
    			}
    	}
   }

    
	/**
	 * @param p - Predicado con las propiedades de las soluciones buscadas
	 * @return Conjunto de soluciones con las propiedades requeridas
	 */
	public Set<S> getSolucionesFiltradas(Predicate<S> p) {
		return soluciones.stream()
				.filter(p)
				.collect(Collectors.<S>toSet());
	}
	
}
