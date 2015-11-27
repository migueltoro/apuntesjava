package us.lsi.flowgraph.ejemplos;


import java.util.Map;










import us.lsi.algoritmos.Algoritmos;
import us.lsi.flowgraph.FlowAlgorithm;
import us.lsi.flowgraph.FlowEdge;
import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraphFileExporter;
import us.lsi.flowgraph.FlowVertex;
import us.lsi.graphs.*;
import us.lsi.pl.AlgoritmoPL;
import us.lsi.pl.ProblemaPL;

public class TestFlow {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*		String[] f1 = {"A","1","0.","12.","5."};
		String[] f2 = {"B","0"};
		String[] f3 = {"A","C","0.","inf","0."};
		Nodo n1 = Nodo.factory.createVertex(f1);
		Nodo n2 = Nodo.factory.createVertex(f2);
		Arista a = Arista.factory.createEdge(n1,n2,f3);
		System.out.println(n1+","+n2+","+a);
*/		FlowGraph f = FlowGraph.create(FlowEdge::createEdge);
		f = (FlowGraph) GraphsReader.newGraph("flow.txt", FlowVertex::createVertex, FlowEdge::createEdge,f);
		System.out.println(f);
		
		FlowAlgorithm ff = FlowAlgorithm.create(f);
		ProblemaPL p = ff.getProblemaLP();
		AlgoritmoPL a = Algoritmos.createPL(p);
		a.ejecuta();
		Map<FlowEdge,Double> edgeFlow = ff.getEdgeFlow(a);
		Map<FlowVertex,Double> sourceFlow = ff.getSourceFlow(a);
		Map<FlowVertex,Double> sinkFlow = ff.getSinkFlow(a);
		for(FlowEdge a1:edgeFlow.keySet()){
			System.out.println(a1.getFrom().getNombre()+","+a1.getTo().getNombre()+","+(edgeFlow.get(a1)<0.00001?0.:edgeFlow.get(a1)));
		}
		System.out.println("--------");
		
		for(FlowVertex nd:sourceFlow.keySet()){
			System.out.println(nd.getNombre()+","+(sourceFlow.get(nd)<0.00001?0.:sourceFlow.get(nd)));
		}
		System.out.println("--------");
		
		for(FlowVertex nd:sinkFlow.keySet()){
			System.out.println(nd.getNombre()+","+(sinkFlow.get(nd)<0.00001?0.:sinkFlow.get(nd)));
		}
			System.out.println(p.toStringConstraints());

//		Graphs2.saveFile(f, "salida2.gsv", new StringNameProvider<Nodo>(), null,null, null,null);
		GraphsFileExporter.saveFile(f, "salida.gsv");
		FlowGraphFileExporter.saveFileFlow(f, "salida7.gsv",true);
		FlowGraphFileExporter.saveFileFlowSoutions(f, "salida8.gsv", sourceFlow,sinkFlow,edgeFlow,true);
	}
	
	
	
}
