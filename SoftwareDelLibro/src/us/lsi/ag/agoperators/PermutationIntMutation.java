package us.lsi.ag.agoperators;

import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import com.google.common.collect.Lists;

import us.lsi.ag.agchromosomes.ListIntegerChromosome;
import us.lsi.common.ParInteger;
import us.lsi.math.Math2;

/**
 * @author Miguel Toro
 *
 *<p> Permuta los valores de dos casillas elegidas al azar
 */
public class PermutationIntMutation implements MutationPolicy {

	public PermutationIntMutation() {
	}

	@Override
	public Chromosome mutate(Chromosome cr) throws MathIllegalArgumentException {
		ListIntegerChromosome c = (ListIntegerChromosome) cr;
		int d = c.getLength();
		List<Integer> ls = Lists.newArrayList(c.decode());
		ParInteger p= Math2.getParAleatorioYDistinto(0, d);		
		Collections.swap(ls, p.p1, p.p2);
		return new ListIntegerChromosome(ls);
	}

}
