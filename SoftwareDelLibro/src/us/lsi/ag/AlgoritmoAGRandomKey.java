package us.lsi.ag;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import   org.apache.commons.math3.genetics.*;

import us.lsi.common.Lists2;


/**
 * <p> Algoritmo específico para algoritmos genéticos con codificación real </p>
 * <p>Es adecaudo para resolver problemas que se representan por permutacionanes </p> 
 * <p>Los cromosomas que implementemos deben heredar de la clase 
 * <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/RandomKey.html" target="_blank"> RandomKey </a>
 * </p>
 * 
 * 
 * @author Miguel Toro
 *
 * 
 */
public class AlgoritmoAGRandomKey extends AlgoritmoAG<Double> {
	
	private static List<Integer> getNormalSequence(ProblemaAGBag<?> problema) {
		return IntStream.range(0,problema.getNumeroDeObjetos())
				.boxed()
				.map(x->Lists2.nCopias(problema.getMultiplicidadMaxima(x),x).stream())
				.flatMap(x->x)
				.collect(Collectors.toList());
	}
	
	public AlgoritmoAGRandomKey(ProblemaAGBag<?> problema) {
		super();
		super.mutationPolicy = new RandomKeyMutation();
		AlgoritmoAG.DIMENSION = IntStream.range(0,problema.getNumeroDeObjetos()).map(x->problema.getMultiplicidadMaxima(x)).sum();
		BagRandomKeyChromosome.normalSequence = getNormalSequence(problema);
		
	}
	
	@Override
	public Chromosome getInitialChromosome() {
		return BagRandomKeyChromosome.getInitialChromosome();
	}
	
	
	
}
