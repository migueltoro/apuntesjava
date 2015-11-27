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

public class TestFlow2 {

	public static void main(String[] args){

		FlowGraph f = FlowGraph.create(FlowEdge::createEdge);
		f = (FlowGraph) GraphsReader.newGraph("flow3.txt", FlowVertex::createVertex, FlowEdge::createEdge,f);
		System.out.println(f);
		
		FlowAlgorithm ff = FlowAlgorithm.create(f, false);
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
		GraphsFileExporter.saveFile(f, "salida20.gv");
		FlowGraphFileExporter.saveFileFlow(f, "salida21.gv",true);
		FlowGraphFileExporter.saveFileFlowSoutions(f, "salida22.gv", sourceFlow,sinkFlow,edgeFlow,true);
	}
	

}
