package us.lsi.astar.jarras;

import org.jgrapht.GraphPath;


import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.jarras.Accion;
import us.lsi.pd.jarras.ProblemaJarrasPD;


public class Test {

	

	public static void main(String[] args) {
		Accion.iniciaAcciones("./ficheros/acciones.txt");
		Accion.acciones.forEach(x->System.out.println(x));
		
		ProblemaJarrasPD.capacidadJarra1 = 4;
		ProblemaJarrasPD.capacidadJarra2 = 3;
		ProblemaJarrasPD.cantidadFinalEnJarra1 = 2;
		ProblemaJarrasPD.cantidadFinalEnJarra2 = 0;
		ProblemaJarrasPD.numMaxAcciones = 10;
		
		VertexJarras origen = VertexJarras.createOrigen();
		VertexJarras destino = VertexJarras.createDestino();
		GraphJarras g = new GraphJarras(origen, destino);
		DijkstraShortestPath<VertexJarras,EdgeJarras<VertexJarras>> d = Algoritmos.createDijkstra(g);
		GraphPath<VertexJarras,EdgeJarras<VertexJarras>> p = d.getPath(origen, destino);
		System.out.println(p);
		System.out.println(p.getWeight());
		System.out.println(p.getVertexList());
	}

}
