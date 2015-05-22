package us.lsi.ag.mochila;

import java.util.List;

import us.lsi.ag.Cromosoma;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.pd.mochila.ObjetoMochila;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class ProblemaMochilaAG3 implements ProblemaAGBag<SolucionMochila>{

	public ProblemaMochilaAG3(String fichero) {
		ProblemaMochila.leeObjetosDisponibles(fichero);
	}	

	@Override
	public SolucionMochila getSolucion(Cromosoma<Integer> chromosome) {
		SolucionMochila s = SolucionMochila.create();
		List<Integer> ls = chromosome.decode();
		for (int i=0; i< this.getNumeroDeObjetos();i++) {
			s =	s.add(ProblemaMochila.getObjeto(i),ls.get(i));
		}
		return s;
	}
	
	

	private Double fitness = null;
	
	@Override
	public Double fitnessFunction(List<Integer> ls) {
		double r;
		double valor=0.;
		double dif=0.;
		double peso=0.;
//		System.out.println("Lista ="+ls);
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
//		System.out.println(fitness+","+dif+","+valor+","+peso);
		return fitness;
	}

	
	public List<ObjetoMochila> getObjetos() {
		return ProblemaMochila.getObjetosDisponibles();
	}
	
	@Override
	public Integer getMultiplicidadMaxima(int index){
		return ProblemaMochila.getObjetosDisponibles().get(index).getNumMaxDeUnidades();
	}

	@Override
	public Integer getNumeroDeObjetos() {
		return this.getObjetos().size();
	}
	

}
