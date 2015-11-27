package us.lsi.flowgraph;



import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.SimpleDirectedGraph;

import com.google.common.base.Preconditions;


public class FlowGraph extends SimpleDirectedGraph<FlowVertex, FlowEdge> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Un vértice de una red de Flujo puede ser una Fuente, un Sumidero o
	 * un vértice intermedio
	 *
	 */
	public enum TipoDeVertice{Source,Sink,Intermedio};	

	public static FlowGraph create(EdgeFactory<FlowVertex, FlowEdge> arg0) {
		return new FlowGraph(arg0);
	}

	public static FlowGraph create(Class<? extends FlowEdge> arg0) {
		return new FlowGraph(arg0);
	}

	private FlowGraph(Class<? extends FlowEdge> arg0) {
		super(arg0);
		Preconditions.checkArgument(check());
	}

	private FlowGraph(EdgeFactory<FlowVertex, FlowEdge> arg0) {
		super(arg0);		
	}
	
	public boolean check(){
		boolean r = true;
		for(FlowVertex v: this.vertexSet()){
			if(this.isSource(v)){
				r = this.incomingEdgesOf(v).isEmpty();
			}
			if(!r) break;
			if(this.isSink(v)){
				r = this.outgoingEdgesOf(v).isEmpty();
			}
			if(!r) break;
		}
		return r;
	}
	
	@Override
	public double getEdgeWeight(FlowEdge edge) {
		return edge.getMax();
	}
	public double getMinEdgeWeight(FlowEdge edge) {
		return edge.getMin();
	}
	public double getUnitEdgeWeight(FlowEdge edge) {
		return edge.getCost();
	}

	public boolean isSource(FlowVertex vertex) {
		return vertex.getTipo()==TipoDeVertice.Source?true:false;
	}

	public boolean isSink(FlowVertex vertex) {
		return vertex.getTipo()==TipoDeVertice.Sink?true:false;
	}

	public double getMaxVertexWeight(FlowVertex vertex) {
		return vertex.getMax();
	}

	public double getMinVertexWeight(FlowVertex vertex) {
		return vertex.getMin();
	}

	public double getUnitVertexWeight(FlowVertex vertex) {
		return vertex.getCost();
	}
	
}
