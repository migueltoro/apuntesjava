package us.lsi.pd;



import java.util.*;
import java.util.stream.Collectors;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Lists2;
import us.lsi.pd.ProblemaPD.Tipo;
import us.lsi.tiposrecursivos.Tree;

import com.google.common.collect.*;

/**
 * <p> Algoritmo que implementa la técnica de Programación Dinámica con sus variantes. 
 * Un problema que se quiera resolver con esta técnica debe implementar el interface ProblemaPD &lt; S,A &gt; y asumimos que 
 * queremos minimizar la propiedad getObjetivo() </p>
 * 
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema14.pdf" target="_blank">Tema14</a></p>
 * 
 * 
 * @author Miguel Toro
 *
 * @param <S> El tipo de la solución
 * @param <A> El tipo de la alternativa
 */
public class AlgoritmoPD<S,A> extends AbstractAlgoritmo {
       
	public static boolean conFiltro = false;
	/**
	 * Si se quiere aplicar la técnica aleatoria para escoger una de las alternativas
	 */
	public static boolean isRandomize = false;
	/**
	 * Tamaño umbral a partir del cual se escoge aleatoriamente una de las alternativas
	 */
	public static Integer sizeRef = 10;
	
	public Map<ProblemaPD<S,A>,Sp<A>> solucionesParciales;
	private ProblemaPD<S,A> problema;    
    private Iterable<ProblemaPD<S,A>> problemas;
    private Double mejorValor;
    private Tipo tipo;
    private Integer numeroDeProblemas;
    private BiMap<ProblemaPD<S,A>,Integer> idsDeProblemas;
    private BiMap<Integer,ProblemaPD<S,A>> problemasDesdeId;
    
    private boolean isMin(){
    	return tipo.equals(Tipo.Min);
    }
    
    private boolean isMax(){
    	return tipo.equals(Tipo.Max);
    }
    
    /**
	 * @return El mejor valor de la propiedade objetivo del problema inicial encontrado por el algoritmo hasta este momento
	 */
	public Double getMejorValor() {
		return mejorValor;
	}
    
	/**
	 * @return Las soluciones parciales de los problemas resueltos
	 */
	public Map<ProblemaPD<S, A>, Sp<A>> getSolucionesParciales() {
		return solucionesParciales;
	}

	/**
	 * @param p - Problema del que se quiere obtener la solución parcial
	 * @return Solución parcial del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	public Sp<A> getSolucionParcial(ProblemaPD<S,A> p) {
			return solucionesParciales.get(p);
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @return Número de problemas resueltos
	 */
	public Integer getNumeroDeProblemas() {
		return numeroDeProblemas;
	}
	
	/**
	 * @param p Un problema
	 * @return El identificador del problema
	 */
	public Integer getIdDeProblema(ProblemaPD<S, A> p) {
		return idsDeProblemas.get(p);
	}

	/**
	 * @return Número de subproblemas encontrado al resolver el problema inicial
	 */
	public int getNumeroDeSubproblemas(){
		return this.solucionesParciales.keySet().size();
	}
	
	/**
	 * @param id Un identificador de un problema
	 * @return El problema asociado al identificador
	 */
	public ProblemaPD<S, A> getProblemasDesdeId(Integer id) {
		return problemasDesdeId.get(id);
	}

	/**
	 * @param p Un problema 
	 * @return Si p es un subproblema ya resuelto al resolver el problema inicial
	 */
	public boolean haSidoResueltoYa(ProblemaPD<S,A> p){
		return this.solucionesParciales.containsKey(p);
	}
	
	/**
	 * @pre el problema es de minimización o de maximización
	 * @param p Un problema
	 * @return Un árbol con las alternativas que están dentro de la solución
	 */
	public Tree<A> getAlternativasDeSolucion(ProblemaPD<S,A> p){
		Tree<A> r = null;
		if (!p.esCasoBase()) {
			Sp<A> sp = this.getSolucionParcial(p);
			List<Tree<A>> la = Lists.newArrayList();
			Integer np = p.getNumeroSubProblemas(sp.alternativa);
			for (Integer i = 0; i < np; i++) {
				Tree<A> rh = this.getAlternativasDeSolucion(p.getSubProblema(sp.alternativa, i));
				if (rh!=null) {
					la.add(rh);
				}
			}
			r = Tree.nary(sp.alternativa,la);
		}
		return r;
	}
	
	public AlgoritmoPD(Iterable<ProblemaPD<S,A>> ps){		
	    problema = Iterables.get(ps, 0);
	    problemas = ps;
	    tipo = problema.getTipo();
	    this.numeroDeProblemas = 0;
	    this.idsDeProblemas = HashBiMap.create();
	    this.problemasDesdeId = this.idsDeProblemas.inverse();
	    mejorValor = isMin()? Double.MAX_VALUE: Double.MIN_VALUE;  
	}
	
	public void ejecuta() {
		if (AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.setTiempoDeEjecucionInicial();
		solucionesParciales = Maps.newHashMap();
		for (ProblemaPD<S, A> p : problemas) {
			do {
				pD(p);
			} while (isRandomize && solucionesParciales.get(problema) == null);
		}
		if (AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.setTiempoDeEjecucionFinal();
	}
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p Un problema
	 * @param alternativas Sus alternativas
	 * @return Una alternativa escogida al azar o todas ellas dependiendo del tamaño del problam
	 */
	private List<A> randomize(ProblemaPD<S,A> p, List<A> alternativas){
		List<A> alt;
		if(isRandomize && p.size()>sizeRef){
			List<A> ls = Lists.newArrayList(alternativas);			
			alt = Lists2.randomUnitary(ls);
		}else{
			alt = alternativas;
		}
		return alt;
	}
	
	private void actualizaMejorValor(ProblemaPD<S,A> p){
			Double objetivo = p.getObjetivo();
			if (objetivo!=null &&
					(isMin() && objetivo < mejorValor || 
					 isMax()&& objetivo > mejorValor)) {
				if (AlgoritmoPD.metricasOK)
					AlgoritmoPD.metricas.addActualizacionMejorValor();
				mejorValor = objetivo;
			}
	}
	
	protected void guardaEnMemoria(ProblemaPD<S, A> p, Sp<A> e) {
		this.numeroDeProblemas++;
		this.idsDeProblemas.put(p,numeroDeProblemas);
		solucionesParciales.put(p, e);
	}
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p El problema a resolver 
	 * @return Su solución parcial
	 */
	private Sp<A> pD(ProblemaPD<S,A> p){
		if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addLLamadaRecursiva();		
		Sp<A> e = null;	
		if (haSidoResueltoYa(p)){
			if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addUsoDeLaMemoria();
			e = getSolucionParcial(p);
		} else if(p.esCasoBase()) {
			if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addCasoBase();
			e = p.getSolucionParcialCasoBase();
			guardaEnMemoria(p, e); 
		} else if(p.estaFueraDeRango()){
			if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addFueraDeRango();
		} else {
			List<Sp<A>> solucionesDeAlternativas = Lists.newArrayList(); 
			for(A a: randomize(p,p.getAlternativas())){
				if(conFiltro && isMin() && p.getObjetivoEstimado(a) >= mejorValor) {
					if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addUnFiltro();
					continue;
				}
				if(conFiltro && isMax() && p.getObjetivoEstimado(a) <= mejorValor) {
					if(AlgoritmoPD.metricasOK) AlgoritmoPD.metricas.addUnFiltro();
					continue;
				}
				int numeroDeSubProblemas = p.getNumeroSubProblemas(a);
				List<Sp<A>> solucionesDeSubProblemas = Lists.newArrayList();  
				boolean haySolucion = true;
				for(int i = 0; i < numeroDeSubProblemas; i++){
					ProblemaPD<S,A> pr = p.getSubProblema(a,i); 
					Sp<A> sp = pD(pr);
					if(sp==null) { haySolucion=false; break;}					
					solucionesDeSubProblemas.add(sp);   	    		
				}
				Sp<A> sa = haySolucion?p.getSolucionParcialPorAlternativa(a, solucionesDeSubProblemas): null;
				solucionesDeAlternativas.add(sa);
			}
			solucionesDeAlternativas = solucionesDeAlternativas.stream().
					filter(x -> x != null).collect(Collectors.toList());
			if (!solucionesDeAlternativas.isEmpty()) {
				e = p.getSolucionParcial(solucionesDeAlternativas);
			}
			if(e!=null) {
				e.solucionesDeAlternativas = solucionesDeAlternativas;
			}
			guardaEnMemoria(p, e); 			
		}
		if (conFiltro && solucionesParciales.get(p)!=null) {
			actualizaMejorValor(p);
		}
		return e;
	}	
	
	
	/**
	 * @param pd - Problema del que se quiere obtener la solución
	 * @return Solución del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	@SuppressWarnings("unchecked")
	public S getSolucion(ProblemaPD<S, A> pd) {	
		S s = null;
		if (solucionesParciales.containsKey(pd)) {
			Sp<A> e = solucionesParciales.get(pd);
			if (e != null) {
				if (pd.esCasoBase()) {					
					s = pd.getSolucionReconstruidaCasoBase(e);
				} else if (e.alternativa != null) {
					List<S> soluciones = Lists.<S> newArrayList();
					for (int i = 0; i < pd.getNumeroSubProblemas(e.alternativa); i++) {
						soluciones.add(getSolucion(pd.getSubProblema(e.alternativa, i)));
					}
					s = pd.getSolucionReconstruidaCasoRecursivo(e, soluciones);
				} else if (e.alternativa == null) {
					s = (S) e.propiedad;
				}
			}
		}
		return s;
	}

	/**
	 * @param nombre - Fichero dónde se almacenará el grafo para ser representado
	 * @param titulo - Título del gráfico
	 * @param pd - Problema y sus subproblemas que forman el grafo
	 */
	public void showAllGraph(String nombre,String titulo,ProblemaPD<S,A> pd){
			
		if (solucionesParciales.get(pd).alternativa!=null) {
			super.setFile(nombre);
			super.getFile().println("digraph "+titulo+" {  \n size=\"100,100\"; ");	
			marcarEnSolucion(pd);
			Set<ProblemaPD<S,A>> visitados = Sets.newHashSet();
			showAll(pd,visitados);
			super.getFile().println("}");
		}
	}
		
	
	private void marcarEnSolucion(ProblemaPD<S,A> pd){
		if(solucionesParciales.containsKey(pd)){
			Sp<A> e = solucionesParciales.get(pd);		
			if(e!=null){
				e.estaEnLaSolucion =true;
				A alternativa = e.alternativa;			
				if (!pd.esCasoBase()) {
					for (int i = 0; i < pd.getNumeroSubProblemas(alternativa); i++) {
						ProblemaPD<S, A> pds = pd.getSubProblema(alternativa, i);
						marcarEnSolucion(pds);
					}
				}	
			}
		}
	}

	private String problema(ProblemaPD<S,A> p, Sp<A> e){
		String s= "    "+"\""+this.idsDeProblemas.get(p)+"\"";
		if(e!=null){
			if (p.esCasoBase()) {
				s = s + " [shape=box, style=dotted, label=\"" + p + "\"]";
			}else{
				s = s + " [shape=box, label=\"" + p + "\"]";
			}
		} else{
			s = s+" [shape=diamond, label=\""+p+"\"]";
		}
		return s+";";
	}
	
	private String alternativa(ProblemaPD<S,A> p, A alternativa){
		String s = "    "+"\""+this.idsDeProblemas.get(p)+","+alternativa+"\""+" [label="+alternativa+"]";
		return s+";";
	}
	
	private String aristaProblemaToAlternativa(ProblemaPD<S,A> p, A alternativa, Sp<A> e){
		String s = "    "+"\""+this.idsDeProblemas.get(p)+"\""+" -> "+"\""+this.idsDeProblemas.get(p)+","+alternativa+"\"";
		if(e.estaEnLaSolucion && e.alternativa.equals(alternativa)){
			s = s+ "[style=bold,arrowhead=dot]";
		}
		return s+";";
	}
	
	private String aristaAlternativaToProblema(ProblemaPD<S,A> p, A alternativa, ProblemaPD<S,A> ps, Sp<A> e){
		String s = "    "+"\""+this.idsDeProblemas.get(p)+","+alternativa+"\""+" -> "+"\""+this.idsDeProblemas.get(ps)+"\"";
		if(e.estaEnLaSolucion && e.alternativa.equals(alternativa)){
			s = s+ "[style=bold,arrowhead=dot]";
		}
		return s+";";
	}


	private void showAll(ProblemaPD<S, A> p, Set<ProblemaPD<S,A>> visitados) {
		if(visitados.contains(p)) return;
		visitados.add(p);
		Sp<A> e = solucionesParciales.get(p);
		super.getFile().println(problema(p, e));
		if (!p.esCasoBase()) {
				for (Sp<A> solParAlt : e.solucionesDeAlternativas) {
					super.getFile().println(alternativa(p, solParAlt.alternativa));
					super.getFile().println(aristaProblemaToAlternativa(p,solParAlt.alternativa, e));
					for (int i = 0; i < p.getNumeroSubProblemas(solParAlt.alternativa); i++) {
						ProblemaPD<S, A> pds = p.getSubProblema(solParAlt.alternativa, i);
						super.getFile().println(aristaAlternativaToProblema(p,solParAlt.alternativa, pds, e));
						showAll(pds,visitados);
					}
				}
		}
	}
	
	/**
	 * Un tipo diseñado para representar soluciones parciales a partir de las cuales 
	 * se puede reconstruir la solución del problema. 
	 * Esta formado por un par: una alternativa y el valor de una propiedad. La solución del problema 
	 * es la que se obtendría tomando la alternativa y el valor estaría en la propiedad
	 * 
	 * El valor null para este tipo representará la no existencia de solución
	 * 
	 * @param <A1> Tipo de la alternativa
	 */
	public static class Sp<A1> implements Comparable<Sp<A1>> {
		

		public A1 alternativa;
		public Double propiedad;
		private List<Sp<A1>> solucionesDeAlternativas = null; 
		private boolean estaEnLaSolucion = false;
					
		public static <A2> Sp<A2> create(A2 alternativa,Double propiedad){
			return new Sp<A2>(alternativa, propiedad);
		}

		protected Sp(A1 alternativa, Double propiedad) {
			super();
			if(propiedad==null) throw new IllegalArgumentException("Propiedad es null");
			this.alternativa = alternativa;
			this.propiedad = propiedad;	
		}		
		
		@Override
		public String toString(){
			return "("+alternativa+","+propiedad+")";
		}

		@Override
		public int compareTo(Sp<A1> ob) {
			return this.propiedad.compareTo(ob.propiedad);
		}
		
	}	
	
}
