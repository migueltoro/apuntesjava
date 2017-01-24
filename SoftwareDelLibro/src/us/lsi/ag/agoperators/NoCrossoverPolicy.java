package us.lsi.ag.agoperators;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ChromosomePair;
import org.apache.commons.math3.genetics.CrossoverPolicy;

public class NoCrossoverPolicy implements CrossoverPolicy {

	public NoCrossoverPolicy() {
		super();
	}

	@Override
	public ChromosomePair crossover(Chromosome chr0, Chromosome chr1) throws MathIllegalArgumentException {
		ChromosomePair r = new ChromosomePair(chr0,chr1);
		return r;
	}

}
