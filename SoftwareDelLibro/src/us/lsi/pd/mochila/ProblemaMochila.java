package us.lsi.pd.mochila;


import java.util.Comparator;
import java.util.List;



import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.stream.Stream2;

import com.google.common.base.Preconditions;


/**
 * <p> Esta clase implementa el tipo ProblemaMochila. Los objetos correspondientes son problemas generalizados de la mochila. </p>
 * <p> Las propiedades de estos problemas son: </p>
 * <ul>
 * <li> Capacidad
 * <li> Index 
 * <li> Objetos Disponibles (propiedad compartida)
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class ProblemaMochila {
	
	private static List<ObjetoMochila> objetosDisponibles;
	private static Comparator<ObjetoMochila> ordenObjetos;
	public static Integer capacidadInicial;

	/**
	 * El método lee el fichero de entrada y actualiza la lista ObjetosDisponibles que queda ordenada 
	 * según el orden natural de los objetos 
	 * 
	 * @param fichero Fichero que contiene las propiedades de los objetos disponibles. Un objeto por línea
	 */
	public static void leeObjetosDisponibles(String fichero) {
		ordenObjetos = Comparator.reverseOrder();
		objetosDisponibles = Stream2.fromFile(fichero)
				.<ObjetoMochila> map((String s) -> ObjetoMochila.create(s))
				.sorted(ordenObjetos)
				.collect(Collectors.<ObjetoMochila> toList());
	}
	
	public static List<ObjetoMochila> getObjetosDisponibles() {
		return objetosDisponibles;
	}
	
	public static Comparator<ObjetoMochila> getOrdenObjetos() {
		return ordenObjetos;
	}	
	
	public static ProblemaMochila create(Integer capacidad, Integer index) {
		return new ProblemaMochila(capacidad, index);
	}
	
	public static ProblemaMochila create() {
		return new ProblemaMochila(ProblemaMochila.capacidadInicial, 0);
	}
	
	private Integer capacidad;	
	private Integer index;

	protected ProblemaMochila() {
		super();
		this.capacidad = ProblemaMochila.capacidadInicial;
		this.index = 0;
	}	
	
	protected ProblemaMochila(Integer capacidad, Integer index) {
		super();
		this.capacidad = capacidad;
		this.index = index;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public Integer getIndex() {
		return index;
	}

	public static ObjetoMochila getObjeto(int index){
		return ProblemaMochila.getObjetosDisponibles().get(index);
	}
	
	public static Integer getValorObjeto(int index){
		return ProblemaMochila.getObjetosDisponibles().get(index).getValor();
	}
	
	public static Integer getPesoObjeto(int index){
		return ProblemaMochila.getObjetosDisponibles().get(index).getPeso();
	}
	
	public static Integer getNumMaxDeUnidades(int index){
		return ProblemaMochila.getObjetosDisponibles().get(index).getNumMaxDeUnidades();
	}
	
	public Integer getNumMaxDeUnidades(){
		return ProblemaMochila.getNumMaxDeUnidades(this.index);
	}
	
	/**
	 * @param index Indice del problema this
	 * @param capacidad Capacidad del problema this
	 * @return Número entero de unidades del objeto que está en la posición index que caben en un mochila de la capacidad dada
	 */
	public static Integer numeroEnteroMaximoDeUnidades(Integer index, Integer capacidad){	
		return Math.min(capacidad/ProblemaMochila.getPesoObjeto(index), ProblemaMochila.getNumMaxDeUnidades(index)) ;	
	}
	
	/**
	 * @param index Indice del problema this
	 * @param capacidad Capacidad del problema this
	 * @return Número real de unidades del objeto que está en la posición index que caben en un mochila de la capacidad dada
	 */
	public static Double numeroRealMaximoDeUnidades(Integer index, Double capacidad){	
		return Math.min(capacidad/ProblemaMochila.getPesoObjeto(index), ProblemaMochila.getNumMaxDeUnidades(index)) ;	
	}
	
	/**
	 * @pre a está contenida en getAlternativas()
	 * @param a Alterantiva escogida
	 * @return Subproblema de this si se escoge la alternativa a.
	 */
	public ProblemaMochila getSubProblema(Integer a) {
		int index = this.getIndex();
		return ProblemaMochila.create(this.getCapacidad()-a*ProblemaMochila.getPesoObjeto(index),index+1);
	}
	
	/**
	 * @pos Si this es final no tiene alternativas, si es un caso base tiene una sola alternativa que maximiza el valor,
	 * si la capacidad es cero hay una sola alternativa igual cero.
	 * @return Las alternativas posibles para this
	 */
	public IntStream getAlternativas() {
		IntStream r;
		if (this.isFinal()) {
			r = IntStream.empty();
		} else if (this.getCapacidad() == 0) {
			r = IntStream.of(0);
		} else if (this.getIndex() == ProblemaMochila.getObjetosDisponibles().size()) {
			r = IntStream.of(this.getNumMaxDeUnidades());
		} else {
			r = IntStream.rangeClosed(
					0,
					ProblemaMochila.numeroEnteroMaximoDeUnidades(
							this.getIndex(), this.getCapacidad()));
		}
		return r;
	}
	
	/**
	 * @pre p es un subproblema de this
	 * @param p Un problema mochila
	 * @return La alternativa que lleva de this a p
	 */
	public Integer getAlternativa(ProblemaMochila p) {
		Preconditions.checkArgument(esSubproblema(p));
		int index1 = this.index;
		int index2 = p.index;
		Preconditions.checkArgument(index2-index1 == 1);
		int capacidad1 = this.capacidad;
		int capacidad2 = p.capacidad;
		int peso = ProblemaMochila.getPesoObjeto(index1);
		Preconditions.checkArgument((capacidad1-capacidad2)%peso ==0);		
		return (capacidad1-capacidad2)/peso;
	}
	
	/**
	 * @param p Un problema mochila
	 * @return Si p es un suproblema de this
	 */
	public boolean esSubproblema(ProblemaMochila p) {
		int index1 = this.index;
		int index2 = p.index;
		if(index2-index1 != 1) return false;
		int capacidad1 = this.capacidad;
		int capacidad2 = p.capacidad;
		int peso = ProblemaMochila.getPesoObjeto(index1);
		return (capacidad1-capacidad2)%peso ==0;
	}
	
	/**
	 * @return Si this es un problema final
	 */
	public boolean isFinal(){
		return this.index == ProblemaMochila.getObjetosDisponibles().size();
	}
		
	/**
	 * @return Una cota superior del valor de la solución del problema this
	 */
	public Integer getCotaSuperiorValorEstimado(){
		Double r = 0.;
		Double c = (double)getCapacidad();
		Double nu;
		int index = getIndex();
		while(true) {
			if(index >= ProblemaMochila.objetosDisponibles.size() || c<= 0.) break;		
			nu = ProblemaMochila.numeroRealMaximoDeUnidades(index,c);	
			r = r+nu*ProblemaMochila.getValorObjeto(index);
			c = c-nu*ProblemaMochila.getPesoObjeto(index);
			index++;						
		} 
		return (int)Math.ceil(r);
	}
	
	/**
	 * @pre a está contenida en getAlternativas()
	 * @param a Una alternativa de this
	 * @return Una cota superior del valor de la solución del problema this si se escoge la alternativa a
	 */
	public Integer getCotaSuperiorValorEstimado(Integer a){
		Double r = 0.;
		Double c = (double)getCapacidad();
		Double nu =(double) a;
		int index = getIndex();
		while(true) {
			r = r+nu*ProblemaMochila.getValorObjeto(index);
			c = c-nu*ProblemaMochila.getPesoObjeto(index);
			index++;
			if(index > ProblemaMochila.objetosDisponibles.size() || c<= 0.) break;
			nu = ProblemaMochila.numeroRealMaximoDeUnidades(index,c);			
		} 
		return (int)Math.ceil(r);
	}
	/**
	 * @return Una cota inferior del valor de la solución del problema this
	 */
	public Integer getCotaInferiorValorEstimado() {
		Integer r = 0;
		Integer c = getCapacidad();
		Integer nu;;
		int index = getIndex();
		while(true) {
			if(index > ProblemaMochila.objetosDisponibles.size() || c<= 0.) break;
			nu = ProblemaMochila.numeroEnteroMaximoDeUnidades(index,c);	
			r = r+nu*ProblemaMochila.getValorObjeto(index);
			c = c-nu*ProblemaMochila.getPesoObjeto(index);
			index++;					
		} 
		return (int)Math.ceil(r);
	}
	/**
	 * @pre a está contenida en getAlternativas()
	 * @param a Una alternativa de this
	 * @return Una cota inferior del valor de la solución del problema this si se escoge la alternativa a
	 */
	public Integer getCotaInferiorValorEstimado(Integer a) {
		Integer r = 0;
		Integer c = getCapacidad();
		Integer nu = a;
		int index = getIndex();
		while(true) {
			r = r+nu*ProblemaMochila.getValorObjeto(index);
			c = c-nu*ProblemaMochila.getPesoObjeto(index);
			index++;
			if(index > ProblemaMochila.objetosDisponibles.size() || c<= 0.) break;
			nu = ProblemaMochila.numeroEnteroMaximoDeUnidades(index,c);			
		} 
		return (int)Math.ceil(r);
	}
	
	@Override
	public String toString(){
		return "("+capacidad+","+index+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((capacidad == null) ? 0 : capacidad.hashCode());
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProblemaMochila))
			return false;
		ProblemaMochila other = (ProblemaMochila) obj;
		if (capacidad == null) {
			if (other.capacidad != null)
				return false;
		} else if (!capacidad.equals(other.capacidad))
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}
	
	/**
	 * @param s Una posible solución
	 * @return s es una solcuión de this si su peso es menor que la capacidad de la mochila y el número de unidades de
	 * cada objeto menor o igual que el número máximo de unidades permitido
	 */
	public boolean isSolucion(SolucionMochila s){
		boolean r = s.getPeso() <= this.getCapacidad();
		for(ObjetoMochila e: s.elements()){
			if(!r) break;
			r = s.count(e) <= e.getNumMaxDeUnidades();
		}		
		return r;	
	}	
}
