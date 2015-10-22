package us.lsi.ag.agoperators;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.RandomKey;

import us.lsi.ag.agchromosomes.PermutationIndexSubListChromosome;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Un operador de cruce adecaudo para cromosomas de tipo mixto y que reutiliza
 * los operadores de cruce proporcionados en <a href=
 * "http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/package-summary.html"
 * target="_blank"> Apache Genetics </a>
 * 
 * @author Miguel Toro
 *
 */
public class SubListCrossoverPolicy implements CrossoverPolicy {

	protected CrossoverPolicy operatorBin;
	protected CrossoverPolicy operatorKey;

	public SubListCrossoverPolicy(CrossoverPolicy operatorBin, CrossoverPolicy operatorKey) {
		super();
		this.operatorBin = operatorBin;
		this.operatorKey = operatorKey;
	}

	@Override
	public ChromosomePair crossover(Chromosome chr0, Chromosome chr1)
			throws MathIllegalArgumentException {
		if (!(chr0 instanceof PermutationIndexSubListChromosome))
			throw new IllegalArgumentException();
		PermutationIndexSubListChromosome c0 = (PermutationIndexSubListChromosome) chr0;
		if (!(chr1 instanceof PermutationIndexSubListChromosome))
			throw new IllegalArgumentException();
		PermutationIndexSubListChromosome c1 = (PermutationIndexSubListChromosome) chr1;
		ChromosomePair binary = operatorBin.crossover(c0.getBinary(),c1.getBinary());
		Preconditions.checkArgument(binary.getFirst() instanceof BinaryChromosome);
		Preconditions.checkArgument(binary.getSecond() instanceof BinaryChromosome);
		ChromosomePair randomKey = operatorKey.crossover(c0.getRandomKey(),c1.getRandomKey());
		Preconditions.checkArgument(randomKey.getFirst() instanceof RandomKey);
		Preconditions.checkArgument(randomKey.getSecond() instanceof RandomKey);
		ChromosomePair r = new ChromosomePair(new PermutationIndexSubListChromosome(binary.getFirst(),
				randomKey.getFirst()), new PermutationIndexSubListChromosome(binary.getSecond(),
				randomKey.getSecond()));
		return r;
	}
}
