package us.lsi.flowgraph.examples;


import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.SolveFlowGraphProblem;


/**
 * Un ejemplo de red de flujo
 * 
 * @author Miguel Toro
 *
 */
public class Flow2 {

	public static void main(String[] args){

		FlowGraphSolution fs = SolveFlowGraphProblem.solve(
				"./ficheros/flow2.txt",
				"./ficheros/flow2Grafo.gv",
				"./ficheros/flow2Soluciones.gv",
				"./ficheros/flow2Constraints.txt", 
				true, 
				false);		

		System.out.println(fs.getGoal()+","+fs.getOptFlow());
	
	}
	

}
