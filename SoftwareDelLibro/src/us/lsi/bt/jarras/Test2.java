package us.lsi.bt.jarras;


import java.util.Arrays;
import java.util.List;








import java.util.stream.Collectors;

import us.lsi.bt.AlgunosTestsBT;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.jarras.Accion;
import us.lsi.pd.jarras.ProblemaJarrasPD;

public class Test2 {

	

	public static void main(String[] args) {		
		Accion.iniciaAcciones(AlgoritmoPD.raiz+"acciones.txt");
		Accion.acciones.forEach(x->System.out.println(x));
		ProblemaJarrasPD.capacidadJarra1 = 4;
		ProblemaJarrasPD.capacidadJarra2 = 3;
		ProblemaJarrasPD.cantidadFinalEnJarra1 = 2;
		ProblemaJarrasPD.cantidadFinalEnJarra2 = 0;
		ProblemaJarrasPD.numMaxAcciones = 10;
		System.out.println("------");
		
		
		
		EstadoJarras e = EstadoJarras.create();
		
		List<Accion> ls = Arrays.asList(4,6,4,7,0,6)
				.stream().map(x->Accion.acciones.get(x))
				.collect(Collectors.toList());
		System.out.println("Test 1 =====");		
		AlgunosTestsBT.test1(e, ls);
		System.out.println("\n\n\nTest 2 =====\n\n\n");	
		AlgunosTestsBT.test2(e);
	}
	
	

}
