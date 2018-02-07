package us.lsi.ag.mochila;

import java.util.List;


import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.pd.mochila.ObjetoMochila;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class ProblemaMochilaAGRange implements ValuesInRangeProblemAG<Integer,SolucionMochila>{

	
	public ProblemaMochilaAGRange(String fichero) {
		ProblemaMochila.leeObjetosDisponibles(fichero);
	}	

	@Override
	public SolucionMochila getSolucion(ValuesInRangeChromosome<Integer> chromosome) {
		SolucionMochila s = SolucionMochila.create();
		List<Integer> ls = chromosome.decode();
		for (int i=0; i< this.getVariableNumber();i++) {
			s =	s.add(ProblemaMochila.getObjeto(i),ls.get(i));
		}
		return s;
	}
	
	

	private Double fitness = null;
	
	@Override
	public Double fitnessFunction(ValuesInRangeChromosome<Integer> c) {
		List<Integer> ls = c.decode();
		double r;
		double valor=0.;
		double dif=0.;
		double peso=0.;
			peso = 0.;
			valor = 0.;
			for (int i = 0; i < ls.size(); i++) {
				peso = peso + ls.get(i) * ProblemaMochila.getPesoObjeto(i);
				valor = valor + ls.get(i) * ProblemaMochila.getValorObjeto(i);
			}
			dif = ProblemaMochila.capacidadInicial - peso;
			if(dif >= 0.){
				r = valor;
			} else {
				r = valor - 10000*(dif*dif);
			}
			fitness = r;
		return fitness;
	}

	
	public List<ObjetoMochila> getObjetos() {
		return ProblemaMochila.getObjetosDisponibles();
	}
	

	@Override
	public Integer getVariableNumber() {
		return this.getObjetos().size();
	}

	@Override
	public Integer getMax(Integer i) {
		return this.getObjetos().get(i).getNumMaxDeUnidades();
	}

	@Override
	public Integer getMin(Integer i) {
		return 0;
	}

}
