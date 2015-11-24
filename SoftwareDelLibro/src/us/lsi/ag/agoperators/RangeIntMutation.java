package us.lsi.ag.agoperators;

import java.util.List;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;

import us.lsi.ag.ProblemaAGIndex;
import us.lsi.ag.agchromosomes.ListIntegerChromosome;
import us.lsi.math.Math2;

import com.google.common.collect.Lists;

/**
 * @author Miguel Toro
 *
 * <p> Permuta una casilla elegida al azar dándole un nuevo valor en el rango definido en el problema para esa nueva casilla
 */
public class RangeIntMutation implements MutationPolicy {

	/**
	 * Variable que debe ser inicializada por el problema
	 */
	public static ProblemaAGIndex<?> problema;
	
	public RangeIntMutation() {}

	@Override
	public Chromosome mutate(Chromosome cr) throws MathIllegalArgumentException {
		ListIntegerChromosome c = (ListIntegerChromosome) cr;
		int d = c.getLength();
		List<Integer> ls = Lists.newArrayList(c.decode());
		int index = Math2.getEnteroAleatorio(0, d);
		Integer sup = problema.getMax(index);
		Integer v = Math2.getEnteroAleatorio(0, sup+1);	
		ls.set(index,v);
		return new ListIntegerChromosome(ls);
	}

}
