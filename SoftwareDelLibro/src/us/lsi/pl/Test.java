package us.lsi.pl;

import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import com.google.common.collect.Lists;

import us.lsi.common.Arrays2;
import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pd.mochila.SolucionMochila;
import us.lsi.pl.ProblemaPL.TipoDeVariable;

public class Test {

	public static SolucionMochila getSolucion(double[] d){
		SolucionMochila s = SolucionMochila.create();
		for (int i = 0; i < ProblemaMochila.getObjetosDisponibles().size(); i++) {
			s = s.add(ProblemaMochila.getObjeto(i), (int) Math.round(d[i]));
		}
		return s;
	}
	
	public static ProblemaPL getProblemaPL() {
		ProblemaMochila.leeObjetosDisponibles("ficheros\\objetosMochila.txt");
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
		p.setTipoDeTodasLasVariables(TipoDeVariable.Entera);
		p.addSosConstraint(Lists.<Integer>newArrayList(0,1,2,3),3);
		for(int i=0;i<num;i++){
			p.setNombre(i, "c"+ProblemaMochila.getObjeto(i).getCodigo());
		}
		return p;
	}
	

	public static void main(String[] args) {
		String fichero = "C:\\Users\\Boss\\Desktop\\Ficheros\\pruebaGenera.txt";
		ProblemaPL p = Test.getProblemaPL();
		p.toStringConstraints(fichero);
		for(LinearConstraint lc: p.getConstraints()){
			System.out.println(p.toString(lc));
		}
		System.out.println(p.toStringVariablesEnteras());
		AlgoritmoPLI a = AlgoritmoPLI.create(fichero);
		a.ejecuta();
//		System.out.println(p.toStringConstraints());
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(a.getObjetivo());
		System.out.println("________");
		System.out.println("________");
		for(int i=0;i<p.getNumOfVariables();i++){
			System.out.println(i+","+p.getNombre(i));
		}
		
		
	}


}
