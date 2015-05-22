package us.lsi.algoritmos;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.ag.AlgoritmoAGBinary;
import us.lsi.ag.AlgoritmoAGMulti;
import us.lsi.ag.AlgoritmoAGMix;
import us.lsi.ag.AlgoritmoAGRandomKey;
import us.lsi.ag.AlgoritmoAGReal;
import us.lsi.ag.BagBinaryChromosome;
import us.lsi.ag.BagMultiChromosome;
import us.lsi.ag.BagMixChromosome;
import us.lsi.ag.BagRandomKeyChromosome;
import us.lsi.ag.ProblemaAGBag;
import us.lsi.ag.ProblemaAGReal;
import us.lsi.ag.RealBinaryChromosome;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.bt.ProblemaBT;
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
import us.lsi.sa.EstadoSA;
import us.lsi.sa.ProblemaSA;
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
	 * @param <E> El tipo del estado
	 * @param <S> El tipo de la solución
	 * @param <A> El tipo de la alternativa
	 * @param p - Problema a resolver
	 * @return Algoritmo de Simulated Annealing para resolver el problema
	 */
	public static <E extends EstadoSA<E,S,A>,S,A> AlgoritmoSA<E,S,A> createSA(ProblemaSA<E,S,A> p) {
		return new AlgoritmoSA<E,S,A>(p);
	}
	
	
	/**
	 * 
	 * @param <E> El tipo de la solcuión parcial
	 * @param <S> El tipo de la solución
	 * @param p - Problema a resolver
	 * @return Algoritmo de Divide y Vencerás Sin Memoria para resolver el problema
	 */
	public static <S, E> AlgoritmoDyVSM<S,E> createDyVSM(ProblemaDyV<S, E> p) {
		return new AlgoritmoDyVSM<S,E>(p);
	}
	
	/**
	 * 
	 * @param <E> El tipo de la solcuión parcial
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
	 * @param p - Problema a resolver
	 * @return Algoritmo de Backtracking para resolver el problema
	 */
	public static <S, A> AlgoritmoBT<S,A> createBT(ProblemaBT<S, A> p) {
		return new AlgoritmoBT<S,A>(p);
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
	
	
	/**<p> Algoritmo específico para algoritmos genéticos con números reales con codificación binaria.
	 *
	 * 
	 *
	 *
	 * @param p Problema
	 * @return AlgoritmoAGReal
	 */
	
	public static AlgoritmoAGReal createAGReal(ProblemaAGReal<?> p) {
		RealBinaryChromosome.limites = p.getLimites();
		RealBinaryChromosome.numeroDeVariables = p.getNumeroDeVariables();
		RealBinaryChromosome.fitnessFunction = p::fitnessFunction;
		return new AlgoritmoAGReal(p);
	}
	
	/**<p> Algoritmo específico para algoritmos genéticos con codificación binaria.
	 * Son adecuados para resolver problemas de subconjuntos de multiconjuntos 
	 * y aquellos otros donde el esapcio de búsqueda es un producto cartesiano de número reales.</p>
	 * Los cromosomas que implementemos deben heredar de la clase 
	 * <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/BinaryChromosome.html" target="_blank"> BinaryChromosome </a>
	 * 
	 * 
	 *
	 *
	 * @param p Problema
	 * @return AlgoritmoAGBinary
	 */
	
	public static AlgoritmoAGBinary createAGBinary(ProblemaAGBag<?> p) {
		BagBinaryChromosome.fitnessFunction = p::fitnessFunction;
		return new AlgoritmoAGBinary(p);
	}
	
	/**<p> Algoritmo específico para algoritmos genéticos con codificación binaria.
	 * Son adecuados para resolver problemas de subconjuntos de multiconjuntos 
	 * y aquellos otros donde el espacio de búsqueda es un producto cartesiano de número reales.</p>
	 * Los cromosomas que implementemos deben heredar de la clase 
	 * <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/BinaryChromosome.html" target="_blank"> BinaryChromosome </a>
	 * 
	 * 
	 *
	 * @param p Problema
	 * @return AlgoritmoAGMulti
	 */
	
	public static AlgoritmoAGMulti createAGMulti(ProblemaAGBag<?> p) {
		BagMultiChromosome.fitnessFunction = p::fitnessFunction;
		return new AlgoritmoAGMulti(p);
	}
	
	/**
	 * <p> Algoritmo específico para algoritmos genéticos con codificación real. 
	 * Son adecuados para resolver problemas que se representan por permutaciones </p>
	 * los cromosomas que implementemos deben heredar de la clase 
	 * <a href="http://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/genetics/RandomKey.html" target="_blank"> RandomKey </a>
	 * 
	 * 
	 *
	 * @param <S> Tipo de la solución
	 * @param p Problema
	 * @return AlgoritmoAGRandomKey
	 */
	public static <S> AlgoritmoAGRandomKey createAGRandomKey(ProblemaAGBag<?> p) {			
		BagRandomKeyChromosome.fitnessFunction = p::fitnessFunction;
		return new AlgoritmoAGRandomKey(p);
	}
	
	/**
	 * <p> Algoritmo específico para algoritmos genéticos con mixta binaria y real. 
	 * Son adecuados para resolver problemas que se representan permutaciones de subconjuntos de los elementos de una lista. </p>
	 * los cromosomas que implementemos deben heredar de la clase que se indica.
	 * 
	 * 
	 *
	 *
	 * @param p Problema
	 * @return AlgoritmoAGMix
	 */
	public static AlgoritmoAGMix createAGMix(ProblemaAGBag<?> p) {
		BagMixChromosome.fitnessFunction = p::fitnessFunction;
		return new AlgoritmoAGMix(p);
	}
	
	
	/**
	 * @param fichero Fichero que  describe laq red de flujo
	 * @return Algoritmo de red de flujo que resuelve el problema especificado en el fichero
	 */
	public static FlowAlgorithm createFlow(String fichero) {
		FlowGraph f = new FlowGraph(FlowEdge.factory);
		f = (FlowGraph) GraphsReader.newGraph(fichero, FlowVertex.factory, FlowEdge.factory,f);
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
	 * @return Algortimo AStar
	 * 
	 */
	public static <V, E> AStarAlgorithm<V, E> createAStar(
			AStarGraph<V, E> graph, V startVertex, V endVertex) {
		return new AStarAlgorithm<V, E>(graph, startVertex, endVertex);
	}
	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta el  primer vértice que cumple el predicado
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goal Predicado que debe cumplir el vértice destino. 
	 * @return Algortimo AStar
	 */
	
	public static <V, E> AStarAlgorithm<V, E> createAStar(
			AStarGraph<V, E> graph, V startVertex, Predicate<V> goal) {
		return new AStarAlgorithm<V, E>(graph, startVertex, goal);
	}

	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta alcanzar todos los vértices objetivo
	 * 
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo
	 * @param startVertex Vértice origen
	 * @param goalSet Conjunto de vértices objetivo 
	 * @return Algortimo AStar
	 * 
	 */
	public static <V, E> AStarAlgorithm<V, E> createAStar(
			AStarGraph<V, E> graph, V startVertex, Set<V> goalSet) {
		return new AStarAlgorithm<V, E>(graph, startVertex, goalSet);
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
