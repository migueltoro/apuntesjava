package us.lsi.flowgraph.examples;


import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.SolveFlowGraphProblem;



/**
 * Un ejemplo de red de flujo
 * 
 * @author Miguel Toro
 *
 */
public class Flow1 {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		
		FlowGraphSolution fs = SolveFlowGraphProblem.solve(
				"./ficheros/flow1.txt",
				"./ficheros/flow1Grafo.gv",
				"./ficheros/flow1Soluciones.gv",
				"./ficheros/flow1Constraints.txt", 
				true, 
				false);		

		System.out.println(fs.getGoal()+","+fs.getOptFlow());

	}
	
	
	
}
