package us.lsi.algoritmos;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.ProblemAG;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.bt.EstadoBT;
import us.lsi.bt.SolucionBT;
import us.lsi.dyv.AlgoritmoDyVSM;
import us.lsi.dyv.AlgoritmoDyVCM;
import us.lsi.dyv.ProblemaDyV;
import us.lsi.flowgraph.FlowAlgorithm;
import us.lsi.flowgraph.FlowEdge;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowVertex;
import us.lsi.graphs.GraphsReader;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPD;
import us.lsi.pl.AlgoritmoPL;
import us.lsi.pl.AlgoritmoPLI;
import us.lsi.pl.ProblemaPL;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.voraz.AlgoritmoVZ;
import us.lsi.voraz.EstadoVZ;
import us.lsi.voraz.ProblemaVZ;

import com.google.common.collect.Lists;

public class Algoritmos {

	
	
	/**
	 * @param <E> El tipo del estado
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p - Problema a resolver
	 * @return Algoritmo Voraz para resolver el problema
	 */
	public static <E extends EstadoVZ<E,S,A>,S,A> AlgoritmoVZ<E,S,A> createVZ(ProblemaVZ<E,S,A> p) {
		return new AlgoritmoVZ<E,S,A>(p);
	}
	
	/**
	 *
	 * @param tipo El tipo del cromomosoma
	 * @param p Problema
	 * @return AlgoritmoSA
	 */
	
	public static  AlgoritmoSA createSA(ChromosomeType tipo, ProblemAG p) {
		return new AlgoritmoSA(tipo,p);
	}
	
	/**
	 * 
	 * @param <E> El tipo de la solución parcial
	 * @param <S> El tipo de la solución
	 * @param p - Problema a resolver
	 * @return Algoritmo de Divide y Vencerás Sin Memoria para resolver el problema
	 */
	public static <S, E> AlgoritmoDyVSM<S,E> createDyVSM(ProblemaDyV<S, E> p) {
		return new AlgoritmoDyVSM<S,E>(p);
	}
	
	/**
	 * 
	 * @param <E> El tipo de la solución parcial
	 * @param <S> El tipo de la solución
	 * @param p - Problema a resolver
	 * @return Algoritmo de Divide y Vencerás Con Memoria para resolver el problema
	 */
	public static <S, E> AlgoritmoDyVCM<S,E> createDyVCM(ProblemaDyV<S, E> p) {
		return new AlgoritmoDyVCM<S,E>(p);
	}
	
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param e - El estado Inicial del Problema a resolver
	 * @return Algoritmo de Backtracking para resolver el problema
	 */
	public static <S extends SolucionBT, A> AlgoritmoBT<S,A> createBT(EstadoBT<S, A> e) {
		return new AlgoritmoBT<S,A>(e);
	}
	
	
	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p - Problema a resolver 
	 * @return Algoritmo de Programación Dinámica para resolver le problema
	 */
	public static <S, A> AlgoritmoPD<S,A> createPD(ProblemaPD<S, A> p) {
		List<ProblemaPD<S, A>> lis = Lists.newArrayList();
		lis.add(p);
		return new AlgoritmoPD<S, A>(lis);
	}

	/**
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p - Conjunto de problemas a resolver 
	 * @return Algoritmo de Programación Dinámica para resolver los problemas
	 */
	public static <S, A> AlgoritmoPD<S,A> createPD(Iterable<ProblemaPD<S, A>> p) {
		return new AlgoritmoPD<S, A>(p);
	}
	
	
	/**
	 *
	 * @param tipo El tipo del cromomosoma
	 * @param p Problema
	 * @return AlgoritmoAG
	 */
	
	public static AlgoritmoAG createAG(ChromosomeType tipo, ProblemAG p) {
		return new AlgoritmoAG(tipo,p);
	}
		
	/**
	 * @param fichero Fichero que  describe la red de flujo
	 * @return Algoritmo de red de flujo que resuelve el problema especificado en el fichero
	 */
	public static FlowAlgorithm createFlow(String fichero) {
		FlowGraph f = FlowGraph.create(FlowEdge::createEdge);
		f = (FlowGraph) GraphsReader.newGraph(fichero,FlowVertex::create,FlowEdge::createEdge,f,null);
		return FlowAlgorithm.create(f);
	}

	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta el vértice destino
	 * 
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo 
	 * @param startVertex Vértice origen
	 * @param endVertex Vértice destino
	 * @return Algoritmo AStar
	 * 
	 */
	public static <V, E> AStarAlgorithm<V, E> createAStar(
			AStarGraph<V, E> graph, V startVertex, V endVertex) {
		return AStarAlgorithm.create(graph, startVertex, endVertex);
	}
	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta el  primer vértice que cumple el predicado
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goal Predicate que especifica el objetivo
	 * @return Algoritmo AStar
	 */
	
	public static <V, E> AStarAlgorithm<V, E> createAStar(
			AStarGraph<V, E> graph, V startVertex, Predicate<V> goal) {
		return AStarAlgorithm.create(graph, startVertex, goal);
	}

	/**
	 * Un algoritmo  DijkstraShortestPath para ir del vértice de inicio hasta el vértice destino
	 * 
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo 
	 * @return Algoritmo DijkstraShortestPath
	 * 
	 */
	public static <V, E> DijkstraShortestPath<V,E> createDijkstra(Graph<V, E> graph) {
		return new DijkstraShortestPath<V,E>(graph);
	}

	/**
	 * Los tipos involucrados pueden encontrarse en el paquete <a href="https://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/optim/linear/package-summary.html" target="_blank">Apache</a>
	 * 
	 * @param p  Problema de Programación Lineal
	 * @return Un algoritmo para resolver le conjunto de restricciones lineales con variables reales. 
	 * Ignora las declaraciones de varibles no reales y otros tipos de restrcciones distintas de  las lineales
	 */
	public static AlgoritmoPL createPL(ProblemaPL p) {
		return AlgoritmoPL.create(p);
	}
	
	/**
	 * Los tipos involucrados pueden encontrarse en el paquete <a href="https://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/optim/linear/package-summary.html" target="_blank">Apache</a>
	 * 
	 * @param p  Problema de Programación Lineal
	 * @param fichero  Un fichero para guardar las restricciones del problema
	 * @return Un algoritmo para resolver le conjunto de restricciones lineales
	 * Ignora las declaraciones de varibles no reales y otros tipos de restrcciones distintas de  las lineales
	 */
	public static AlgoritmoPL createPL(ProblemaPL p, String fichero) {
		return AlgoritmoPL.create(p,fichero);
	}
	
	
	/**
	 * Los tipos involucrados pueden encontrarse en el paquete <a href="http://lpsolve.sourceforge.net/5.5/" target="_blank">LpSolve</a>
	 * 
	 * @param fichero  Describe un problema de Programación Lineal
	 * @return Un algoritmo para resolver le conjunto de restricciones lineales, variables de tipo real, entero y binarias, 
	 * variables libres y semicontinuas y otros tipos de restricciones como las 
	 * <a href="http://en.wikipedia.org/wiki/Special_ordered_set" target="_blank">Sos</a>
	 * 
	 */
	public static AlgoritmoPLI createPLI(String fichero) {
		return AlgoritmoPLI.create(fichero);
	}
	
	/**
	 * Los tipos involucrados pueden encontrarse en el paquete <a href="http://lpsolve.sourceforge.net/5.5/" target="_blank">LpSolve</a>
	 * 
	 * @param p  Problema de Programación Lineal
	 * @param fichero  Fichero para guardar las restrcciones del problema
	 * @return Un algoritmo para resolver le conjunto de restricciones lineales con variables de tipo real, entero y binarias, 
	 * variables libres y semicontinuas y otros tipos de restricciones como las 
	 * <a href="http://en.wikipedia.org/wiki/Special_ordered_set" target="_blank">Sos</a>
	 * 
	 */
	public static AlgoritmoPLI createPLI(ProblemaPL p, String fichero) {
		return AlgoritmoPLI.create(p,fichero);
	}
}
