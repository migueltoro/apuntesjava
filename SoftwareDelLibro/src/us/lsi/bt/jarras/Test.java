package us.lsi.bt.jarras;


import us.lsi.algoritmos.Algoritmos;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.common.PairInteger;
import us.lsi.pd.jarras.Accion;
import us.lsi.pd.jarras.ProblemaJarrasPD;
import us.lsi.pd.jarras.SolucionJarras;


public class Test {

	

	public static void main(String[] args) {
		
		Accion.iniciaAcciones(AlgoritmoBT.raiz+"acciones.txt");
		Accion.acciones.forEach(x->System.out.println(x));
		ProblemaJarrasPD.capacidadJarra1 = 4;
		ProblemaJarrasPD.capacidadJarra2 = 3;
		ProblemaJarrasPD.cantidadFinalEnJarra1 = 2;
		ProblemaJarrasPD.cantidadFinalEnJarra2 = 0;
		ProblemaJarrasPD.numMaxAcciones = 10;
		AlgoritmoBT.setFile("resultadoBT.txt");
		AlgoritmoBT.calculaMetricas();
		AlgoritmoBT.conFiltro = true;
		System.out.println("------");
		EstadoJarras e = EstadoJarras.create();
		AlgoritmoBT<SolucionJarras, Accion> a = Algoritmos.createBT(e);
		a.ejecuta();

		if (a.getSoluciones().isEmpty()) 
			System.out.println("Solución no encontrada");
		else{
			System.out.println("Solución: " + a.getSolucion());
		}
		System.out.println(AlgoritmoBT.metricas);
		PairInteger p = Accion.ejecuta(PairInteger.create(0, 0), a.getSolucion().getListaAcciones());
		System.out.println("Estado Final = "+p);
	}

}
