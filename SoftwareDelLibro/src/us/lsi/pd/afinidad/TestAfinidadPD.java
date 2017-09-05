package us.lsi.pd.afinidad;


import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPD;

public class TestAfinidadPD {
	public static void main(String[] args) {
		//configuraPD
				
		//creaProblema y lanza algoritmo
		ProblemaAfinidad.create("ficheros/afinidad_test2.txt");
		AlgoritmoPD.calculaMetricas();
		ProblemaPD<SolucionAfinidad, Integer> p= ProblemaAfinidadPD.create();
		AlgoritmoPD.conFiltro = true;
		AlgoritmoPD<SolucionAfinidad, Integer> a= Algoritmos.createPD(p);
		a.ejecuta();
		a.showAllGraph("ficheros/PDAfinidad_22.gv","Afinidad",p);
		//recuperasolución		
		System.out.println(a.getSolucion(p));
		System.out.println(AlgoritmoPD.metricas);
		System.out.println(a.getSolucionesParciales().size()+","+a.getNumeroDeProblemas());
		
	}
}

