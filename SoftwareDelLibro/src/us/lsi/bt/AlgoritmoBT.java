package us.lsi.bt;


import java.util.List;
import java.util.Set;


import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.bt.EstadoBT.Tipo;
import us.lsi.common.Lists2;

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
public class AlgoritmoBT<S extends SolucionBT, A> extends AbstractAlgoritmo {
	
	
	/**
	 * Solución obtenida si es única o la mejor solución si hay varias
	 */
    private S solucion = null;
    /**
	 * Conjunto de soluciones encontradas
	 */
	private Set<S> soluciones; 
	/**
	 * Número de soluciones que se buscan si no se buscan todas y el problema es de tipo Otro
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
	 * Si se usa la técnica con filtro
	 */
	public static Boolean conFiltro = false;
	private Double mejorValor = Double.MAX_VALUE;   
   	
    private EstadoBT<S,A> estado;
	private boolean exito = false;
	private Tipo tipo;
	
	
	 private boolean isMin(){
	    	return tipo.equals(Tipo.Min);
	 }
	    
	 private boolean isMax(){
	    	return tipo.equals(Tipo.Max);
	 }
	 
	 private boolean isTodasLasSoluciones(){
	    	return tipo.equals(Tipo.TodasLasSoluciones);
	 }
	
	 private boolean isAlgunasSoluciones(){
	    	return tipo.equals(Tipo.AlgunasSoluciones);
	 }
	/**
	 * @return El mejor valor de la propiedad objetivo del problema inicial encontrado 
	 * por el algoritmo hasta este momento
	 */
	public Double getMejorValor() {
		return mejorValor;
	}

	/**
	 * @param e - El esatod inicial del problema a resolver
	 */
	public AlgoritmoBT(EstadoBT<S,A> e){
    	this.estado = e; 
    	this.tipo = e.getTipo();
    }
    
    /**
     * Ejecución del algoritmo
     */
	public void ejecuta() {
		if(AlgoritmoBT.metricasOK) AlgoritmoBT.metricas.setTiempoDeEjecucionInicial();
		tipo = estado.getTipo();
	    mejorValor = isMin()? Double.MAX_VALUE: Double.MIN_VALUE;  
    	soluciones =  Sets.newHashSet(); 	
		do {
			estado = estado.getEstadoInicial();
			bt();
		} while(isRandomize && isAlgunasSoluciones()&& soluciones.size()<AlgoritmoBT.numeroDeSoluciones);
		if(AlgoritmoBT.metricasOK) AlgoritmoBT.metricas.setTiempoDeEjecucionFinal();
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
		S s = estado.getSolucion();
		if (s != null) {
			Double objetivo = s.getObjetivo();
			if ((this.isTodasLasSoluciones() || 
					 this.isAlgunasSoluciones() || 
					(this.isMin() && objetivo <= this.mejorValor) || 
					(this.isMax() && objetivo >= this.mejorValor))) {
				solucion = s;
				soluciones.add(solucion);
				this.mejorValor = objetivo;
			}
		}
	}
    
    private void bt() {	
    	if(estado.esCasoBase()){ 
    		if(AlgoritmoBT.metricasOK) AlgoritmoBT.metricas.addCasoBase();
    			actualizaSoluciones();
    		if(this.isAlgunasSoluciones() && soluciones.size()>=AlgoritmoBT.numeroDeSoluciones) 
    			exito = true;
    	} else if(estado.estaFueraDeRango()){
    		if(AlgoritmoBT.metricasOK) AlgoritmoBT.metricas.addFueraDeRango();
    	} else {
    			for(A a: filtraRandomize(estado,estado.getAlternativas())){  
    					if(conFiltro && isMin() && estado.getObjetivoEstimado(a) >= mejorValor) {
    						AlgoritmoBT.metricas.addUnFiltro();
    						continue;
    					}
    					if(conFiltro && isMax() && estado.getObjetivoEstimado(a) <= mejorValor) {
    						AlgoritmoBT.metricas.addUnFiltro();
    						continue;
    					}
    					estado = estado.avanza(a); 
    					bt();  
    					estado = estado.retrocede(a);
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
	
	/**
	 * @return Conjunto de las mejores soluciones
	 */
	public Set<S> getMejoresSoluciones() {
		return soluciones.stream()
				.filter(x->x.getObjetivo().equals(solucion.getObjetivo()))
				.collect(Collectors.<S>toSet());
	}

	public S getSolucion() {
		return solucion;
	}

	public Set<S> getSoluciones() {
		return soluciones;
	}

	public Tipo getTipo() {
		return tipo;
	}
	
	
}
