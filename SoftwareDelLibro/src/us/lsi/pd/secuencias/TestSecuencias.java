package us.lsi.pd.secuencias;

import java.util.List;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.common.Par;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.secuencias.Secuencia.Accion;

public class TestSecuencias {

	

	public static void main(String[] args) {
		
		ProblemaSecuenciasPD p = ProblemaSecuenciasPD.create("cbrrrarreterb","carretera");
		AlgoritmoPD<List<Par<Accion,Integer>>,Accion> a = Algoritmos.createPD(p);
		a.ejecuta();
		System.out.println(a.getSolucion(p));

	}

}
