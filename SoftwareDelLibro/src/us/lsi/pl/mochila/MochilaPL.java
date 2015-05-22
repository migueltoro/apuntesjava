package us.lsi.pl.mochila;


import org.apache.commons.math3.optim.linear.Relationship;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.common.Arrays2;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;
import us.lsi.pl.AlgoritmoPL;
import us.lsi.pl.ProblemaPL;


public class MochilaPL {

	public static SolucionMochila getSolucion(double[] d){
		SolucionMochila s = SolucionMochila.create();
		for (int i = 0; i < ProblemaMochila.getObjetosDisponibles().size(); i++) {
			s = s.add(ProblemaMochila.getObjeto(i), (int) Math.round(d[i]));
		}
		return s;
	}
	
	public static ProblemaPL getProblemaPL() {
		ProblemaMochila.leeObjetosDisponibles("objetosMochila.txt");
		ProblemaMochila.capacidadInicial = 78;
		int num = ProblemaMochila.getObjetosDisponibles().size();
		ProblemaPL p = ProblemaPL.create(num, ProblemaPL.TipoDeOptimizacion.Max);
		double [] d = Arrays2.getArrayDouble(num, 1.);
		for (int i = 0; i < num ; i++) {
			d[i] = ProblemaMochila.getValorObjeto(i);
		}
		p.setObjectiveFunction(d, 0.);
		d = Arrays2.getArrayDouble(num, 0.);
		for (int i = 0; i < num ; i++) {
			d[i] = ProblemaMochila.getPesoObjeto(i);
		}
		p.addConstraint(d, Relationship.LEQ, ProblemaMochila.capacidadInicial);
		for (int i = 0; i < ProblemaMochila.getObjetosDisponibles().size(); i++) {
			d = Arrays2.getArrayDouble(num, 0.);
			d[i] = 1.;
			p.addConstraint(d, Relationship.LEQ, ProblemaMochila.getNumMaxDeUnidades(i));
		}	
		return p;
	}
	

	public static void main(String[] args) {
		ProblemaPL p = getProblemaPL();
		int num = ProblemaMochila.getObjetosDisponibles().size();
		AlgoritmoPL a = Algoritmos.createPL(p);
		a.ejecuta();
		System.out.println(p.toStringConstraints());
		for (int i = 0; i < num; i++) {
			System.out.println("Num Unidades de "+ ProblemaMochila.getObjeto(i).getCodigo()+ " = "+a.getSolucion()[i]+" Num Max =" +ProblemaMochila.getNumMaxDeUnidades(i));
		}
		System.out.println("________");
		System.out.println(a.getObjetivo());
		SolucionMochila s = MochilaPL.getSolucion(a.getSolucion());
		System.out.println("________");
		System.out.println(s);
	}

}
