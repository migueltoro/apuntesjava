package us.lsi.ag;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import   org.apache.commons.math3.genetics.*;

import us.lsi.common.Lists2;



/**
 * <p> Algoritmo específico para algoritmos genéticos con codificación binaria </p>
 * <p> Son adecuados para resolver problemas que buscan subconjuntos de un multiconjunto o conjuntos de valores que puedan
 * ser codificados mediante una lista de bits. Un caso concreto es el de los números reales o productos cartesianos 
 * de los mismos</p>
 * <p> Los cromosomas que implementemos deben heredar de la clase 
 * <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/BinaryChromosome.html" target="_blank"> BinaryChromosome </a>
 * 
 * 
 * @author Miguel Toro
 *
 *
 */
public class AlgoritmoAGBinary  extends AlgoritmoAG<Integer> {
	
	
	private static List<Integer> getNormalSequence(ProblemaAGBag<?> problema) {
			return IntStream.range(0,problema.getNumeroDeObjetos())
					.boxed()
					.map(x->Lists2.nCopias(problema.getMultiplicidadMaxima(x),x).stream())
					.flatMap(x->x)
					.collect(Collectors.toList());
	}
	
	public AlgoritmoAGBinary(ProblemaAGBag<?> problema) {
		super();
		super.mutationPolicy = new BinaryMutation();	
		AlgoritmoAG.DIMENSION = IntStream.range(0,problema.getNumeroDeObjetos()).map(x->problema.getMultiplicidadMaxima(x)).sum();
		BagBinaryChromosome.normalSequence = getNormalSequence(problema);
	}
	
	

	@Override
	public Chromosome getInitialChromosome() {
		return BagBinaryChromosome.getInitialChromosome();
	}
	
	
			
}
