package us.lsi.flowgraph.ejemplos;

import us.lsi.flowgraph.FlowGraphFileExporter;

public class TestFlow3 {

	
	public static String ruta = "C:\\Users\\Boss\\Desktop\\Ficheros\\";
	
	public static void main(String[] args) {
		FlowGraphFileExporter.solveProblemFlow("flow3.txt", ruta
				+ "problemaFlow3.gv", ruta + "solucionesProblemaFlow3.gv", ruta
				+ "restriccionesFlow3.txt", true, true);
	}

}
