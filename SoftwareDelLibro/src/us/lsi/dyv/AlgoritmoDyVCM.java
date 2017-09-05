package us.lsi.dyv;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import us.lsi.algoritmos.AbstractAlgoritmo;

/**
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema12.pdf" target="_blank">Tema12</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 * @param <E> Tipo de la solución intermedia
 */

public class AlgoritmoDyVCM<S,E> extends AbstractAlgoritmo {
	private Map<ProblemaDyV<S,E>, E> solucionesParciales;
	private ProblemaDyV<S,E> problema;
	private E solucionParcial;
	
	public AlgoritmoDyVCM(ProblemaDyV<S,E> p) {
		problema = p;
	}

	
	/**
	 * Ejecución del algoritmo
	 */
	public void ejecuta() {

		solucionesParciales = Maps.newHashMap();
		solucionParcial = dYV(problema);
				
	}

	private E dYV(ProblemaDyV<S,E> p) {
		E s;
		if (solucionesParciales.containsKey(p)) {
			s = solucionesParciales.get(p);
		} else if (p.esCasoBase()) {
			s = p.getSolucionCasoBase();
				solucionesParciales.put(p, s);				
		} else {
			int numeroDeSubProblemas = p.getNumeroDeSubProblemas();		
			List<E> soluciones = Lists.newArrayList();  			
			for(int i = 0; i < numeroDeSubProblemas; i++){
				ProblemaDyV<S,E> pr = p.getSubProblema(i);
	    		s = dYV(pr);
	    		soluciones.add(s);  
			}
			s = p.combina(soluciones);
				solucionesParciales.put(p, s);
		}
		return s;
	}	
	/**
	 * @return La solución del problema si existe o null
	 */
	public S getSolucion() {
		return problema.getSolucion(solucionParcial);
	}
	
	
	
}
