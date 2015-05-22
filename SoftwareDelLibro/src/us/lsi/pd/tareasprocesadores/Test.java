package us.lsi.pd.tareasprocesadores;

import java.util.List;
import java.util.Map;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;


public class Test {

	
	public static void main(String[] args) {
		TareasProcesadoresPD p = TareasProcesadoresPD.create(AlgoritmoPD.getRaiz()+"tareas.txt",3);
		System.out.println(Tarea.tareas);
		AlgoritmoPD<Map<Integer,List<Tarea>>,Integer> a = Algoritmos.createPD(p);
		a.ejecuta();
		System.out.println("Solucion Del Problema " +a.getSolucionParcial(p));
		System.out.println(a.getSolucion(p));
		a.showAllGraph("C:\\Users\\Boss\\Desktop\\Ficheros\\Procesadores.gv", "Grafo", p);
	}

}
