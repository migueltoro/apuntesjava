package us.lsi.ag.mochila;

import java.util.List;

import us.lsi.ag.Cromosoma;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.pd.mochila.ObjetoMochila;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;

public class ProblemaMochilaAG implements ProblemaAGBag<SolucionMochila> {

	public ProblemaMochilaAG(String fichero) {
		ProblemaMochila.leeObjetosDisponibles(fichero);
	}	

	@Override
	public SolucionMochila getSolucion(Cromosoma<Integer> chromosome) {
		SolucionMochila s = SolucionMochila.create();
		for (Integer e: chromosome.decode()) {
			s =	s.add(ProblemaMochila.getObjeto(e),1);
		}
		return s;
	}
	
	

	@Override
	public Double fitnessFunction(List<Integer> ls) {
		double r;
		Integer peso = 0;
		Integer valor = 0;
    	for (Integer e: ls) {
			peso = peso + ProblemaMochila.getPesoObjeto(e);
			valor = valor + ProblemaMochila.getValorObjeto(e);
		}
		double dif = ProblemaMochila.capacidadInicial - peso;
		if(dif >= 0.){
			r = valor;
		} else {
			r = valor - 10000*(dif*dif);
		}
		return r;
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
