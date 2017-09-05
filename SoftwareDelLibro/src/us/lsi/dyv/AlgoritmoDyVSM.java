package us.lsi.dyv;

import java.util.*;

import us.lsi.algoritmos.AbstractAlgoritmo;

import com.google.common.collect.*;



/**
 * <p>La documentación puede encontarse en el: <a href="../../../document/Tema12.pdf" target="_blank">Tema12</a></p>
 * 
 * @author Miguel Toro
 *
 * @param <S> Tipo de la solución
 * @param <E> Tipo de la solución intermedia
 */
public class AlgoritmoDyVSM<S,E> extends AbstractAlgoritmo {
	
	private ProblemaDyV<S,E> problema;
	private E solucionParcial;
	
	public AlgoritmoDyVSM(ProblemaDyV<S,E> p) {
		problema = p;
	}

	
	/**
	 * Ejecución del algoritmo
	 */
	public void ejecuta() {

		solucionParcial = dYV(problema);
				
	}

	private E dYV(ProblemaDyV<S,E> p) {
		E s;
		if (p.esCasoBase()) {
			s = p.getSolucionCasoBase();
		} else {
			int numeroDeSubProblemas = p.getNumeroDeSubProblemas();		
			List<E> soluciones = Lists.newArrayList();  			
			for(int i = 0; i < numeroDeSubProblemas; i++){
				ProblemaDyV<S,E> pr = p.getSubProblema(i);
	    		s = dYV(pr);
	    		soluciones.add(s);  
			}
			s = p.combina(soluciones);
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

