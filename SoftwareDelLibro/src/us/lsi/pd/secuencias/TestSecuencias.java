package us.lsi.pd.secuencias;


import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.secuencias.Secuencia.Accion;

public class TestSecuencias {

	

	public static void main(String[] args) {
		
		ProblemaSecuenciasPD p = ProblemaSecuenciasPD.create("cbrrrarreterb","carretera");
		AlgoritmoPD.conFiltro = true;
		AlgoritmoPD<SolucionSecuencias,Accion> a = Algoritmos.createPD(p);
		a.ejecuta();
		System.out.println(a.getSolucion(p));

	}

}
