package us.lsi.ag.agchromosomes;

import org.apache.commons.math3.genetics.Chromosome;

public interface IChromosome<T> {
	T decode();
	double fitness();
	Chromosome asChromosome();
}
