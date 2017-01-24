package us.lsi.pd;



import java.util.*;
import java.util.stream.Collectors;

import us.lsi.algoritmos.AbstractAlgoritmo;
import us.lsi.common.Lists2;
import us.lsi.pd.ProblemaPD.Tipo;

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
    private static Double mejorValor;
    private static Tipo tipo;

    
    private boolean isMin(){
    	return tipo.equals(Tipo.Min);
    }
    
    private boolean isMax(){
    	return tipo.equals(Tipo.Max);
    }
    
    /**
	 * @return El mejor valor de la propiedade objetivo del problema inicial encontrado por el algoritmo hasta este momento
	 */
	public static Double getMejorValor() {
		return mejorValor;
	}
    
	public AlgoritmoPD(Iterable<ProblemaPD<S,A>> ps){		
	    problema = Iterables.get(ps, 0);
	    problemas = ps;
	    tipo = problema.getTipo();
	    mejorValor = isMin()? Double.MAX_VALUE: Double.MIN_VALUE;  
	}
	
	public void ejecuta() {
		
		do {
			solucionesParciales = Maps.newHashMap();
			for (ProblemaPD<S, A> p : problemas) {					
					pD(p);				
			}		
		} while (isRandomize && solucionesParciales.get(problema)==null);			
	}
	
	private Iterable<A> randomize(ProblemaPD<S,A> p, Iterable<A> alternativas){
		Iterable<A> alt;
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
		if(isMin() && objetivo < mejorValor || isMax() && objetivo > mejorValor) {
			mejorValor = objetivo;
		}
	}
	
	private Sp<A> pD(ProblemaPD<S,A> p){
		Sp<A> e = null;	
		if (solucionesParciales.containsKey(p)){
			e = solucionesParciales.get(p);
		} else if( p.esCasoBase()) {
			e = p.getSolucionCasoBase();	        
			solucionesParciales.put(p, e); 					
		} else {
			List<Sp<A>> solucionesDeAlternativas = Lists.newArrayList(); 
			for(A a: randomize(p,p.getAlternativas())){
				if(isMin() && p.getObjetivoEstimado(a) >= mejorValor) continue;
				if(isMax() && p.getObjetivoEstimado(a) <= mejorValor) continue;
				int numeroDeSubProblemas = p.getNumeroSubProblemas(a);
				List<Sp<A>> solucionesDeSubProblemas = Lists.newArrayList();  
				boolean haySolucion = true;
				for(int i = 0; i < numeroDeSubProblemas; i++){
					ProblemaPD<S,A> pr = p.getSubProblema(a,i); 				
					Sp<A> sp = pD(pr);
					if(sp==null) { haySolucion=false; break;}					
					solucionesDeSubProblemas.add(sp);   	    		
				}
				Sp<A> sa = haySolucion?p.combinaSolucionesParciales(a, solucionesDeSubProblemas): Sp.create(a,null);
				solucionesDeAlternativas.add(sa);
			}
			solucionesDeAlternativas = solucionesDeAlternativas.stream().filter(x -> x.propiedad != null).collect(Collectors.toList());
			if (!solucionesDeAlternativas.isEmpty()) {
				e = p.seleccionaAlternativa(solucionesDeAlternativas);
			}
			if(e!=null) {
				e.solucionesDeAlternativas = solucionesDeAlternativas;
			}
			solucionesParciales.put(p, e); 			
		}
		actualizaMejorValor(p);	
		return e;
	}
	
	/**
	 * @param pd - 
	 * @return Si pd es un subproblema encontrado al resolver el problema inicial
	 */
	public boolean isSubproblema(ProblemaPD<S,A> pd){
		return this.solucionesParciales.containsKey(pd);
	}
	
	/**
	 * @return Número de subproblemas encontrado al resolver el problema inicial
	 */
	public int getNumeroDeSubproblemas(){
		return this.solucionesParciales.keySet().size();
	}
	
	/**
	 * @param pd - Problema del que se quiere obtener la solución parcial
	 * @return Solución parcial del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	public Sp<A> getSolucionParcial(ProblemaPD<S,A> pd) {
		Sp<A> e = null;		
		if(solucionesParciales.containsKey(pd)){
			e = solucionesParciales.get(pd);
		}
		return  e;
	}
	
	/**
	 * @param pd - Problema del que se quiere obtener la solución
	 * @return Solución del problema o null si no tiene solución o no ha ha sido encontrado por el algoritmo
	 */
	public S getSolucion(ProblemaPD<S, A> pd) {	
		S s = null;
		if (solucionesParciales.containsKey(pd)) {
			Sp<A> e = solucionesParciales.get(pd);
			if (e != null) {
				if (pd.esCasoBase()) {					
					s = pd.getSolucionReconstruida(e);
				} else if (e.alternativa != null) {
					List<S> soluciones = Lists.<S> newArrayList();
					for (int i = 0; i < pd.getNumeroSubProblemas(e.alternativa); i++) {
						soluciones.add(getSolucion(pd.getSubProblema(e.alternativa, i)));
					}
					s = pd.getSolucionReconstruida(e, soluciones);
				} else if (e.alternativa == null) {
					List<S> solucionesAlternativas = Lists.<S> newArrayList();
					for (Sp<A> e1 : e.solucionesDeAlternativas) {
						List<S> soluciones = Lists.<S> newArrayList();
						if(e1.propiedad == null) continue;
						for (int i = 0; i < pd.getNumeroSubProblemas(e1.alternativa); i++) {
							soluciones.add(getSolucion(pd.getSubProblema(e1.alternativa, i)));
						}
						s = pd.getSolucionReconstruida(e1, soluciones);
						solucionesAlternativas.add(s);
					}
					s = pd.getSolucionReconstruida(e,solucionesAlternativas);
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
		super.setFile(nombre);
		super.getFile().println("digraph "+titulo+" {  \n size=\"100,100\"; ");		
		showAll(pd);
		super.getFile().println("}");
	}
	
	private void marcarEnSolucion(ProblemaPD<S,A> pd){
		if(solucionesParciales.containsKey(pd)){
			Sp<A> e = solucionesParciales.get(pd);		
			if(e!=null){
				e.estaEnLaSolucion =true;
				A alternativa = e.alternativa;			
				if (!pd.esCasoBase()) {
					for (int i = 0; i < pd.getNumeroSubProblemas(alternativa); i++) {
						ProblemaPD<S, A> pds = pd.getSubProblema(
								alternativa, i);
						marcarEnSolucion(pds);
					}
				}	
			}
		}
	}

	private String problema(ProblemaPD<S,A> p, Sp<A> e){
		String s= "    "+"\""+p+"\"";
		if(e!=null){
			s = s+" [shape=box]";
		} else{
			s = s+" [shape=diamond]";
		}
		return s+";";
	}
	
	private String alternativa(ProblemaPD<S,A> p, A alternativa){
		String s = "    "+"\""+p+","+alternativa+"\""+" [label="+alternativa+"]";
		return s+";";
	}
	
	private String aristaProblemaToAlternativa(ProblemaPD<S,A> p, A alternativa, Sp<A> e){
		String s = "    "+"\""+p+"\""+" -> "+"\""+p+","+alternativa+"\"";
		if(e.estaEnLaSolucion && e.alternativa.equals(alternativa)){
			s = s+ "[style=bold,arrowhead=dot]";
		}
		return s+";";
	}
	
	private String aristaAlternativaToProblema(ProblemaPD<S,A> p, A alternativa, ProblemaPD<S,A> ps, Sp<A> e){
		String s = "    "+"\""+p+","+alternativa+"\""+" -> "+"\""+ps+"\"";
		if(e.estaEnLaSolucion && e.alternativa.equals(alternativa)){
			s = s+ "[style=bold,arrowhead=dot]";
		}
		return s+";";
	}


	private void showAll(ProblemaPD<S,A> p){		
		if (solucionesParciales.get(p).alternativa!=null) {
			marcarEnSolucion(p);
		}
		for(ProblemaPD<S,A> pd:solucionesParciales.keySet()){			
			Sp<A> e = solucionesParciales.get(pd);
			if(e!=null)super.getFile().println(problema(pd,e));
			if(e!=null && e.solucionesDeAlternativas!=null){			
				for(Sp<A> solParAlt:e.solucionesDeAlternativas){			
					super.getFile().println(alternativa(pd,solParAlt.alternativa));
					super.getFile().println(aristaProblemaToAlternativa(pd,solParAlt.alternativa,e));
					for(int i = 0; i < pd.getNumeroSubProblemas(solParAlt.alternativa); i++){	
						ProblemaPD<S,A> pds= pd.getSubProblema(solParAlt.alternativa,i);
						if(solucionesParciales.get(pds)==null)super.getFile().println(problema(pds,null));						
						super.getFile().println(aristaAlternativaToProblema(pd,solParAlt.alternativa,pds,e));
					}
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
