package us.lsi.ag;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.BinaryChromosome;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.RandomKey;

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
public class MixCrossoverPolicy implements CrossoverPolicy {

	protected CrossoverPolicy operator;

	public MixCrossoverPolicy(CrossoverPolicy operator) {
		super();
		this.operator = operator;
	}

	@Override
	public ChromosomePair crossover(Chromosome chr0, Chromosome chr1)
			throws MathIllegalArgumentException {
		if (!(chr0 instanceof BagMixChromosome))
			throw new IllegalArgumentException();
		BagMixChromosome c0 = (BagMixChromosome) chr0;
		if (!(chr1 instanceof BagMixChromosome))
			throw new IllegalArgumentException();
		BagMixChromosome c1 = (BagMixChromosome) chr1;
		ChromosomePair binary = operator.crossover(c0.getBinary(),
				c1.getBinary());
		Preconditions.checkArgument(binary.getFirst() instanceof BinaryChromosome);
		Preconditions.checkArgument(binary.getSecond() instanceof BinaryChromosome);
		ChromosomePair randomKey = operator.crossover(c0.getRandomKey(),c1.getRandomKey());
		Preconditions.checkArgument(randomKey.getFirst() instanceof RandomKey);
		Preconditions.checkArgument(randomKey.getSecond() instanceof RandomKey);
		ChromosomePair r = new ChromosomePair(new BagMixChromosome(binary.getFirst(),
				randomKey.getFirst()), new BagMixChromosome(binary.getSecond(),
				randomKey.getSecond()));
		return r;
	}
}
