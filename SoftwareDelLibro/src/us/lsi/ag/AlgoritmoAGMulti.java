package us.lsi.ag;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.Chromosome;

import com.google.common.collect.Lists;

public class AlgoritmoAGMulti extends AlgoritmoAG<Integer> {
	
	public AlgoritmoAGMulti(ProblemaAGBag<?> problema) {
		super();	
		super.mutationPolicy = new BinaryMutation();	
		BagMultiChromosome.numeroDeObjetos = problema.getNumeroDeObjetos();
		AlgoritmoAG.DIMENSION = BagMultiChromosome.numeroDeBits*BagMultiChromosome.numeroDeObjetos;
		BagMultiChromosome.multiplicidades = Lists.newArrayList();
		for(int i=0; i< BagMultiChromosome.numeroDeObjetos;i++){
			BagMultiChromosome.multiplicidades.add(problema.getMultiplicidadMaxima(i));
		}
	}
	
	@Override
	public Chromosome getInitialChromosome() {
		return BagMultiChromosome.getInitialChromosome();
	}
}
