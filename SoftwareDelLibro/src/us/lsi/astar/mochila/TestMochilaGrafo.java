package us.lsi.astar.mochila;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.pd.mochila.ProblemaMochila;


public class TestMochilaGrafo {

	public static void main(String[] args) {
		
		ProblemaMochila.leeObjetosDisponibles("./ficheros/objetosmochila.txt");
		System.out.println(ProblemaMochila.getObjetosDisponibles());
		ProblemaMochila.capacidadInicial = 534;
		MochilaVertex p1 = MochilaVertex.create();
		System.out.println("Problema Inicial = "+p1);
		Predicate<MochilaVertex> goal = (MochilaVertex p)-> ProblemaMochila.getObjetosDisponibles().size()==p.getProblema().getIndex();
		AStarGraph<MochilaVertex,SimpleEdge<MochilaVertex>> graph = MochilaGrafo.create(p1);
		AStarAlgorithm<MochilaVertex,SimpleEdge<MochilaVertex>> a = Algoritmos.createAStar(graph, p1, goal);
		GraphPath<MochilaVertex,SimpleEdge<MochilaVertex>> path = a.getPath();
		System.out.println(a.getPathVertexList());
		System.out.println(MochilaVertex.getSolucion(a.getPathVertexList()));
		System.out.println(path);
	}
}
