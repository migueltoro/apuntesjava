package us.lsi.ag;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.genetics.Chromosome;

import us.lsi.common.Lists2;


/**
 * <p> Algoritmo específico para resolver problemas que buscan permutaciones de subconjuntos de un multiconjunto </p>
 * <p> Los cromosomas que implementemos deben heredar de la clase {@link us.lsi.ag.AlgoritmoAGMix AlgoritmoAGMix} </p>
 * 
 * 
 * 
 * @author Miguel Toro
 *
 * 
 */

public class AlgoritmoAGMix extends AlgoritmoAG<Integer> {
	
	private static List<Integer> getNormalSequence(ProblemaAGBag<?> problema) {
		return IntStream.range(0,problema.getNumeroDeObjetos())
				.boxed()
				.map(x->Lists2.nCopias(problema.getMultiplicidadMaxima(x),x).stream())
				.flatMap(x->x)
				.collect(Collectors.toList());
	}
	
	public AlgoritmoAGMix(ProblemaAGBag<?> problema) {
		super();
		super.mutationPolicy = new MixMutationPolicy();
		super.crossOverPolicy = new MixCrossoverPolicy(super.crossOverPolicy);
		AlgoritmoAG.DIMENSION = IntStream.range(0,problema.getNumeroDeObjetos()).map(x->problema.getMultiplicidadMaxima(x)).sum();
		BagMixChromosome.normalSequence = getNormalSequence(problema);
 	}
	
	@Override
	public Chromosome getInitialChromosome() {
		return BagMixChromosome.getInitialChromosome();
	}
	
}
