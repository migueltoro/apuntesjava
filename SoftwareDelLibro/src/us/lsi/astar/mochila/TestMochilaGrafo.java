package us.lsi.astar.mochila;

import java.util.function.Function;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleEdge;
import us.lsi.pd.mochila.ProblemaMochila;


public class TestMochilaGrafo {

	public static void main(String[] args) {
		ProblemaMochila.leeObjetosDisponibles("objetosmochila.txt");
		System.out.println(ProblemaMochila.getObjetosDisponibles());
		ProblemaMochila.capacidadInicial = 78;
		MochilaVertex p1 = MochilaVertex.create();
		System.out.println("Problema Inicial = "+p1);
		Function<MochilaVertex,Double> d = (MochilaVertex p)-> 1.*ProblemaMochila.getObjetosDisponibles().size() - p.getProblema().getIndex();
		AStarGraph<MochilaVertex,SimpleEdge<MochilaVertex>> graph = MochilaGrafo.create(SimpleEdge::create);
		AStarAlgorithm<MochilaVertex,SimpleEdge<MochilaVertex>> a = Algoritmos.createAStar(graph, p1, d);
		GraphPath<MochilaVertex,SimpleEdge<MochilaVertex>> path = a.getPath();
		System.out.println(Graphs.getPathVertexList(path));
		System.out.println(MochilaVertex.getSolucion(Graphs.getPathVertexList(path)));
		System.out.println(a.getPathLength());
	}
}
