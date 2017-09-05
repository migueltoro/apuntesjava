package us.lsi.flowgraph.examples;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;








import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.flowgraph.FlowGraphSolution;
import us.lsi.flowgraph.SolveFlowGraphProblem;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.examples.Carretera;
import us.lsi.graphs.examples.Ciudad;

/**
 * 
 * Resuelve le problema del  cálculo de los caminos con vértices y aristas disjuntos mediante una red de flujo
 * 
 * @author Miguel Toro
 *
 */
public class CaminosVerticesDisjuntos {
	
	public static void creaFichero(String fileIn, String fileOut, Ciudad from, Ciudad to){
		
		Graph<Ciudad,Carretera> g =  new SimpleWeightedGraph<Ciudad,Carretera>(Carretera::create);
		g =  GraphsReader.newGraph(fileIn,Ciudad::create, Carretera::create,g,Carretera::getKm);
		
		SimpleDirectedGraph<Ciudad,Carretera> gs = Graphs2.toDirectedGraph((SimpleGraph<Ciudad,Carretera>) g);
		PrintWriter f = null;
		try {
			f = new PrintWriter(new BufferedWriter(new FileWriter(fileOut)));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		
		f.println("#VERTEX#");
		for(Ciudad c:gs.vertexSet()){
			if (c.equals(from)) {
				f.println(c + ",1,0.0,inf,1.0");
			}else if(c.equals(to)){
				f.println(c + ",2,0.0,inf,0.0");
			}else {
				f.println(c + ",0,0.0,1.0,0.0");
			}
		}		
		f.println("#EDGE#");
		for(Carretera cr:gs.edgeSet()){
			Ciudad source = gs.getEdgeSource(cr);
			Ciudad target = gs.getEdgeTarget(cr);
			if (!source.equals(to) && !target.equals(from)) {
				f.println(source + "," + target+ ",0.0,1.0,0.0");
			}
		}
		f.close();
	}
	
	public static void main(String[] args) {
		Ciudad from = Ciudad.create("Cadiz");
		Ciudad to = Ciudad.create("Almeria");
		CaminosVerticesDisjuntos.creaFichero("./ficheros/andalucia.txt","./ficheros/andaluciaFlow1.txt",from,to);
		FlowGraphSolution fs = SolveFlowGraphProblem.solve(
				"./ficheros/andaluciaFlow1.txt", 
				"./ficheros/andaluciaFlowGrafo1.gv", 
				"./ficheros/andaluciaFlowSoluciones1.gv",
				"./ficheros/andaluciaFlowConstraints1.txt",
				true,
				false);
		
		System.out.println(fs.getOptFlow());
		
		System.out.println("====");
		System.out.println("=========="+fs.getWalks(false));
		
	}
}
