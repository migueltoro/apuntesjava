package us.lsi.flowgraph;

import java.util.HashMap;
import java.util.Map;


import org.jgrapht.io.Attribute;
import org.jgrapht.io.ComponentAttributeProvider;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.DefaultAttribute;
import org.jgrapht.io.StringComponentNameProvider;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.common.Files2;
import us.lsi.common.StringExtensions2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.pl.AlgoritmoPL;
import us.lsi.pl.ProblemaPL;

public class SolveFlowGraphProblem {

	/**
	 *
	 * 
	 * <p><strong>Fichero de Entrada</strong></p>
	 * <p>#VERTEX#<br>A,1,0.,15.,0.<br>B,0,0.,11.,0.<br>C,0,4.,6.,2.<br>D,0,0.,10.,0.<br>E,0,0.,8.,0.<br>F,2,0.,15,-1.<br>#EDGE#<br>A,B,3.,9.,0.<br>A,C,0.,7.,0.<br>B,D,6.,13.,0.<br>D,E,4.,15.,0.<br>E,F,3.,6.,0.<br>C,F,0.,8.,0.<br>C,E,0.,2.,0.</p>
	 * <p><strong><span style="color: #000000;">Restricciones</span></strong></p>
	 * <p>max +2.0x1-x8<br>+x0 &lt;= 9.0<br>+x0 &gt;= 3.0<br>+x1 &lt;= 7.0<br>+x2 &lt;= 13.0<br>+x2 &gt;= 6.0<br>+x3 &lt;= 15.0<br>+x3 &gt;= 4.0<br>+x4 &lt;= 6.0<br>+x4 &gt;= 3.0<br>+x5 &lt;= 8.0<br>+x6 &lt;= 2.0<br>-x0-x1+x7 = 0.0<br>+x7 &lt;= 15.0<br>+x0-x2 = 0.0<br>+x0 &lt;= 11.0<br>+x1-x5-x6 = 0.0<br>+x1 &lt;= 6.0<br>+x1 &gt;= 4.0<br>+x2-x3 = 0.0<br>+x2 &lt;= 10.0<br>+x3-x4+x6 = 0.0<br>+x3+x6 &lt;= 8.0<br>+x4+x5-x8 = 0.0<br>+x8 &lt;= 15.0</p>
	 * <p><br><strong>Indices </strong></p>	
	 * <p><br>5,C--F<br>7,A<br>4,E--F<br>2,B--D<br>0,A--B<br>1,A--C<br>6,C--E<br>8,F<br>3,D--E</p>
	 * <p><strong>Grafo del Problema</strong></p>
	 * <p>digraph G {<br> A [ label="A<br>/15.0" style="bold" ];<br> B [ label="B<br>/11.0" ];<br> C [ label="C<br>4.0/6.0" ];<br> D [ label="D<br>/10.0" ];<br> E [ label="E<br>/8.0" ];<br> F [ label="F<br>/15.0" style="dotted" ];<br> A -&gt; B [ label="3.0/9.0" ];<br> A -&gt; C [ label="/7.0" ];<br> B -&gt; D [ label="6.0/13.0" ];<br> D -&gt; E [ label="4.0/15.0" ];<br> E -&gt; F [ label="3.0/6.0" ];<br> C -&gt; F [ label="/8.0" ];<br> C -&gt; E [ label="/2.0" ];<br>}</p>
	 * <p><strong>&nbsp;</strong></p>
	 * <p><a href="../../../../document/problemaLiga.jpg" target="_blank">Grafo del Problema</a></p>
	 * <p><strong>&nbsp;</strong></p>
	 * <p><strong>Grafo con las Soluciones</strong></p>
	 * <p>digraph G {<br> A [ label="A<br>12.0" style="bold" ];<br> B [ ];<br> C [ ];<br> D [ ];<br> E [ ];<br> F [ label="F<br>-12.0" style="dotted" ];<br> A -&gt; B [ label="6.0" ];<br> A -&gt; C [ label="6.0" ];<br> B -&gt; D [ label="6.0" ];<br> D -&gt; E [ label="6.0" ];<br> E -&gt; F [ label="6.0" ];<br> C -&gt; F [ label="6.0" ];<br> C -&gt; E [ label="0.0" ];<br>}</p>
	 * <p><strong>&nbsp;</strong></p>
	 * <p><a href="../../../../document/solucionesLiga.jpeg" target="_blank">Grafo con las Soluciones</a></p>
	 * <p><strong>&nbsp;</strong></p>
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param fileIn Fichero con los datos de entrada de la Red de Flujo
	 * @param fileGraphFlow Fichero que contiene el grafo asociado a la Red de Flujo 
	 * @param fileSolutionsGraphFlow Fichero que contiene el grafo con las soluciones de la Red de Flujo
	 * @param fileConstraints Fichero que contiene las restricciones asociadas a la Red de Flujo
	 * @param conNombres Si queremos que aparezcan los nombres devueltos por toString()
	 * @param min Si queremos minimizar la función objetivo
	 * @return Las propiedades de la solución
	 */
	public static FlowGraphSolution solve(
			String fileIn, 
			String fileGraphFlow, 
			String fileSolutionsGraphFlow,
			String fileConstraints,
			boolean conNombres,
			boolean min) {
		FlowGraph flowGraph = FlowGraph.create(FlowEdge::createEdge);
		flowGraph = (FlowGraph) GraphsReader.newGraph(fileIn, FlowVertex::create, FlowEdge::createEdge, flowGraph, null);
		FlowAlgorithm fa = FlowAlgorithm.create(flowGraph, min);
		ProblemaPL p = fa.getProblemaLP();
		AlgoritmoPL a = Algoritmos.createPL(p);
		a.ejecuta();
		
		Map<FlowEdge, Double> edgeFlow = fa.getEdgeFlow(a);
		Map<FlowVertex, Double> sourceFlow = fa.getSourceFlow(a);
		Map<FlowVertex, Double> sinkFlow = fa.getSinkFlow(a);
		SolveFlowGraphProblem.saveFileFlow(flowGraph, fileGraphFlow, conNombres);
		final FlowGraph fgc = flowGraph;
		
		FlowGraph sg = Graphs2.<FlowVertex,FlowEdge,FlowGraph>subGraph(
				flowGraph, 
				v->fgc.edgesOf(v).stream().anyMatch(y->edgeFlow.get(y)>0), 
				e->edgeFlow.get(e)>0,
				x->FlowGraph.create(x));
		
		SolveFlowGraphProblem.saveFileFlowSoutions(sg, fileSolutionsGraphFlow,
				sourceFlow, sinkFlow, edgeFlow, conNombres);
		String constraints = p.toStringConstraints()+fa.toStringIndex();
		StringExtensions2.toFile(constraints, fileConstraints);
		Double optFlow = sourceFlow.entrySet().stream().mapToDouble(x->x.getValue()).sum();
		Double goal = a.getObjetivo();
		FlowGraphSolution fs = FlowGraphSolution.create(sg, constraints, edgeFlow, sourceFlow, sinkFlow, optFlow, goal);
		return fs;
	}


	public static void saveFileFlow(FlowGraph graph, String file, boolean conNombres) {
		DOTExporter<FlowVertex, FlowEdge> de = new DOTExporter<>(
				new StringComponentNameProvider<FlowVertex>(), 
				null, 
				null,
				new EtiquetasVerticesFlow(graph, conNombres),
				new EtiquetasAristasFlow(graph));
		de.exportGraph(graph, Files2.getWriter(file));
	}

	public static void saveFileFlowSoutions(FlowGraph graph,
			String file, Map<FlowVertex, Double> sourceFlow, Map<FlowVertex, Double> sinkFlow,
			Map<FlowEdge, Double> edgeFlow, boolean conNombres) {

		DOTExporter<FlowVertex, FlowEdge> de = new DOTExporter<>(
				new StringComponentNameProvider<FlowVertex>(), null, null,
				new EtiquetasVerticesSolutionFlow<FlowVertex>(sourceFlow, sinkFlow,conNombres), 
				new EtiquetasAristasSolutionFlow(edgeFlow));
		de.exportGraph(graph, Files2.getWriter(file));
	}

	

	
	private static class EtiquetasVerticesSolutionFlow<V> implements
			ComponentAttributeProvider<V> {

		private Map<V, Double> source;
		private Map<V, Double> sink;
		private boolean conNombres;

		public EtiquetasVerticesSolutionFlow(Map<V, Double> source,
				Map<V, Double> sink, boolean conNombres) {
			super();
			this.source = source;
			this.sink = sink;
			this.conNombres = conNombres;
		}

		@Override
		public Map<String, Attribute> getComponentAttributes(V v) {
			Map<String, Attribute> map = new HashMap<>();
			String nombre = conNombres ? v.toString() + "\\n" : "";
			if (source.containsKey(v)) {
				map.put("style", DefaultAttribute.createAttribute("bold"));
				map.put("label", DefaultAttribute.createAttribute(nombre + source.get(v).toString()));
			} else if (sink.containsKey(v)) {
				map.put("style", DefaultAttribute.createAttribute("dotted"));
				map.put("label", DefaultAttribute.createAttribute(nombre + "-" + sink.get(v)));
			}
			return map;
		}

	}

	private static class EtiquetasVerticesFlow implements ComponentAttributeProvider<FlowVertex> {

		private FlowGraph graph;
		boolean conNombres;

		public EtiquetasVerticesFlow(FlowGraph graph, boolean conNombres) {
			super();
			this.graph = graph;
			this.conNombres = conNombres;
		}

		@Override
		public Map<String, Attribute> getComponentAttributes(FlowVertex v) {
			Map<String, Attribute> map = new HashMap<>();
			String nombre = conNombres ? v.toString() + "\\n" : "";
			String min = graph.getMinVertexWeight(v) > 0 ? ""
					+ graph.getMinVertexWeight(v) : "";
			String max = graph.getMaxVertexWeight(v) < Double.MAX_VALUE ? ""
					+ graph.getMaxVertexWeight(v) : "";
			if (min.length() > 0 || max.length() > 0) {
				map.put("label", DefaultAttribute.createAttribute(nombre + min + "/" + max));
			}
			if (graph.isSource(v)) {
				map.put("style", DefaultAttribute.createAttribute("bold"));
			} else if (graph.isSink(v)) {
				map.put("style", DefaultAttribute.createAttribute("dotted"));
			}
			return map;
		}

	}

	private static class EtiquetasAristasSolutionFlow implements ComponentAttributeProvider<FlowEdge> {

		public EtiquetasAristasSolutionFlow(Map<FlowEdge, Double> m) {
			super();
			this.m = m;
		}

		private Map<FlowEdge, Double> m;

		@Override
		public Map<String, Attribute> getComponentAttributes(FlowEdge a) {
			Map<String, Attribute> map = new HashMap<>();
			if (m.containsKey(a)) {
				map.put("label", DefaultAttribute.createAttribute(m.get(a).toString()));
			}
			return map;
		}
	}

	private static class EtiquetasAristasFlow implements ComponentAttributeProvider<FlowEdge> {

		public EtiquetasAristasFlow(FlowGraph graph) {
			super();
			this.graph = graph;
		}

		private FlowGraph graph;

		@Override
		public Map<String, Attribute> getComponentAttributes(FlowEdge a) {
			Map<String, Attribute> map = new HashMap<>();
			String min = graph.getMinEdgeWeight(a) > 0 ? ""
					+ graph.getMinEdgeWeight(a) : "";
			String max = graph.getEdgeWeight(a) < Double.MAX_VALUE ? ""
					+ graph.getEdgeWeight(a) : "";
			if (min.length() > 0 || max.length() > 0) {
				map.put("label", DefaultAttribute.createAttribute(min + "/" + max));
			}
			return map;
		}
	}

}
