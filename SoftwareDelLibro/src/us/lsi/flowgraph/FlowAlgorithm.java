package us.lsi.flowgraph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.Relationship;

import us.lsi.common.Arrays2;
import us.lsi.pl.ProblemaPL;
import us.lsi.pl.SolutionPL;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Miguel Toro
 *
 */
public class FlowAlgorithm {
	
	
	/**
	 * @param grafo Un grafo que describe una Red de Flujo
	 * @return Un algoritmo que resuelve la Red de Flujo asumiendo 
	 * que se quiere minimizar la función objetivo
	 * @pre Los vértices fuente no tienen aristas de entrada y los vértices sumidero no tienen aristas de salida
	 */
	public static FlowAlgorithm create(FlowGraph grafo) {
		Preconditions.checkArgument(grafo.check(),"El grafo no cumple las condiciones");
		return new FlowAlgorithm(grafo);
	}

	/**
	 * @param grafo Un grafo que describe una Red de Flujo
	 * @param minimiza verdadero si se minimiza la función objetivo 
	 * y falso si se maximiza
	 * @pre Los vértices fuente no tienen aristas de entrada y los vértices sumidero no tienen aristas de salida
	 * @return Un algoritmo que resuelve la Red de Flujo
	 */
	public static FlowAlgorithm create(FlowGraph grafo,boolean minimiza) {
		Preconditions.checkArgument(grafo.check(),"El grafo no cumple las condiciones");
		return new FlowAlgorithm(grafo, minimiza?ProblemaPL.TipoDeOptimizacion.Min:ProblemaPL.TipoDeOptimizacion.Max);
	}

	private FlowGraph grafo;
	private BiMap<VertexOrEdge,Integer> index;
	private BiMap<Integer,VertexOrEdge> inverseIndex;
	private Integer edgeNum;
	private Integer sourceNum;
	private Integer sinkNum;
	private Integer numItems;	
	private ProblemaPL.TipoDeOptimizacion tipo;
	
	private FlowAlgorithm(FlowGraph grafo){
		this(grafo,ProblemaPL.TipoDeOptimizacion.Min);
	}
	
	private FlowAlgorithm(FlowGraph grafo, ProblemaPL.TipoDeOptimizacion tipo) {
		super();
		this.grafo = grafo;
		this.index = HashBiMap.create();
		this.numItems = 0;
		this.edgeNum = 0;
		this.sinkNum = 0;
		this.sourceNum = 0;		
		this.tipo = tipo;
		numerate();
	}
	
	/**
	 * @return Si se minimiza la función objetivo
	 */
	

	/**
	 * @return El grafo que describe la Red de Flujo
	 */
	public FlowGraph getGrafo() {
		return grafo;
	}
	
	
	/**
	 * 
	 * @return ProblemaPL equivalente
	 */
	public ProblemaPL getProblemaLP(){
		LinearObjectiveFunction f = getObjective();
		Collection<LinearConstraint> cs = getConstraints();
		ProblemaPL p = ProblemaPL.create(numItems,tipo);
		p.setObjectiveFunction(f);
		for(LinearConstraint lc : cs){
			p.addConstraint(lc);
		}
		return p;
	}
	
	/**
	 * @return BiMap de entero a Vértice o Arista
	 */
	public BiMap<VertexOrEdge, Integer> getIndex() {
		return index;
	}

	/**
	 * Los enteros asociados a las aristas están en el intervalo [0, getEdgeNum()).
	 * Los enteros asociados a los vértices fuente están en el intervalo [getEdgeNum(), getEdgeNum()+getSourceNum()).
	 * Los enteros asociados a los vértices sumidero están en el intervalo [getEdgeNum()+getSourceNum(), getNumItems()).
	 * @return BiMap Vértice o Arista a entero
	 */
	public BiMap<Integer, VertexOrEdge> getInverseIndex() {
		return inverseIndex;
	}

	/**
	 * @return Número de aristas
	 */
	public Integer getEdgeNum() {
		return edgeNum;
	}

	/**
	 * @return Número de vértices fuente
	 */
	public Integer getSourceNum() {
		return sourceNum;
	}

	/**
	 * @return Número de vértices sumidero
	 */
	public Integer getSinkNum() {
		return sinkNum;
	}

	/**
	 * @return Número de vértices más número de aristas
	 */
	public Integer getNumItems() {
		return numItems;
	}

	/**
	 * @param a Un algoritmo con soluciones al problema lineal equivalente
	 * @return El flujo que circula por cada arista
	 */
	public Map<FlowEdge,Double> getEdgeFlow(SolutionPL a){
		Map<FlowEdge,Double> m = Maps.newHashMap();
		for(int i= 0; i < this.getEdgeNum(); i++){
			m.put(this.getInverseIndex().get(i).edge, a.getSolucion()[i]);
		}
		return m;
	}
	
	/**
	 * @param a Un algoritmo con soluciones al problema lineal equivalente
	 * @return El flujo que fluye de cada vértice fuente
	 */
	public Map<FlowVertex,Double> getSourceFlow(SolutionPL a){
		Map<FlowVertex,Double> m = Maps.newHashMap();
		
		for(int i= this.getEdgeNum(); i< this.getEdgeNum()+this.getSourceNum(); i++){
			m.put(this.getInverseIndex().get(i).vertex, a.getSolucion()[i]);
		}
		return m;
	} 
	
	/**
	 * @param a Un algoritmo con soluciones al problema lineal equivalente
	 * @return El flujo se consume en los vértices sumidero
	 */
	public Map<FlowVertex,Double> getSinkFlow(SolutionPL a){
		Map<FlowVertex,Double> m = Maps.newHashMap();
		for(int i= this.getEdgeNum()+this.getSourceNum(); i < this.getNumItems(); i++){
			m.put(this.getInverseIndex().get(i).vertex, a.getSolucion()[i]);
		}
		return m;
	}
	
	private Collection<LinearConstraint> getConstraints() {
		List<LinearConstraint> constraints = Lists.newArrayList();
		LinearConstraint lc;
		for(FlowEdge edge:grafo.edgeSet()){
			if(grafo.getEdgeWeight(edge)==grafo.getMinEdgeWeight(edge)){
				lc = getEdgeEqualConstrainst(edge,grafo.getEdgeWeight(edge));
				constraints.add(lc);
			} else {
				if (grafo.getEdgeWeight(edge) < Double.MAX_VALUE) {
					lc = getEdgeMaxConstrainst(edge,grafo.getEdgeWeight(edge));
					constraints.add(lc);
				}
				if (grafo.getMinEdgeWeight(edge) > 0.) {
					lc = getEdgeMinConstrainst(edge,grafo.getMinEdgeWeight(edge));
					constraints.add(lc);
				}
			}
		}
		for(FlowVertex vertex: grafo.vertexSet()){
			lc = getVertexFlowConstraint(vertex);
			constraints.add(lc);
			if (grafo.isSource(vertex)|| grafo.isSink(vertex)) {
				if (grafo.getMaxVertexWeight(vertex)==grafo.getMinVertexWeight(vertex)){
					lc = getVertexEqualConstrainst(vertex,grafo.getMaxVertexWeight(vertex));
					constraints.add(lc);
				}else{
					if (grafo.getMaxVertexWeight(vertex) < Double.MAX_VALUE) {
						lc = getVertexMaxConstrainst(vertex,grafo.getMaxVertexWeight(vertex));
						constraints.add(lc);
					}
					if (grafo.getMinVertexWeight(vertex) > 0. ) {
						lc = getVertexMinConstrainst(vertex,grafo.getMinVertexWeight(vertex));
						constraints.add(lc);
					}
				}
			} else{
				if (grafo.getMaxVertexWeight(vertex)==grafo.getMinVertexWeight(vertex)){
					lc = getFlowInVertexEqualConstrainst(vertex,grafo.getMaxVertexWeight(vertex));
					constraints.add(lc);
				}else{
					if (grafo.getMaxVertexWeight(vertex) < Double.MAX_VALUE) {
						lc = getFlowInVertexMaxConstrainst(vertex,grafo.getMaxVertexWeight(vertex));
						constraints.add(lc);
					}
					if (grafo.getMinVertexWeight(vertex) > 0. ) {
						lc = getFlowInVertexMinConstrainst(vertex,grafo.getMinVertexWeight(vertex));
						constraints.add(lc);
					}
				}
			}
		}
		return constraints;
	}

	

	private VertexOrEdge createVertex(FlowVertex vertex){
		return new VertexOrEdge(VertexOrEdge.Tipo.Vertex,vertex,null);
	}
	
	private VertexOrEdge createEdge(FlowEdge edge){
		return new VertexOrEdge(VertexOrEdge.Tipo.Edge,null,edge);
	}
	
	private void numerate(){
		for(FlowEdge edge:grafo.edgeSet()){
			VertexOrEdge e = createEdge(edge);
			index.put(e, numItems);
			numItems++;
			edgeNum++;
		}
		for(FlowVertex vertex:grafo.vertexSet()){			
			if (grafo.isSource(vertex)) {
				VertexOrEdge v = createVertex(vertex);
				index.put(v, numItems);
				numItems++;
				sourceNum++;
			}
		}
		for(FlowVertex vertex:grafo.vertexSet()){			
			if (grafo.isSink(vertex)) {
				VertexOrEdge v = createVertex(vertex);
				index.put(v, numItems);
				numItems++;
				sinkNum++;
			}
		}
		inverseIndex = index.inverse();		
	}
	
	private Integer edgeNum(FlowEdge edge){
		VertexOrEdge e = createEdge(edge);
		return index.get(e);
	}
	
	private Integer vertexNum(FlowVertex vertex){
		Preconditions.checkArgument(grafo.isSource(vertex)||grafo.isSink(vertex));
		VertexOrEdge e = createVertex(vertex);
		return index.get(e);
	}
	
	public String toStringIndex(){
		String r = "Indices \n";
		for(VertexOrEdge e:index.keySet()){
			r = r+index.get(e)+","+e+"\n";
		}
		return r;
	}
	
	
	private LinearObjectiveFunction getObjective() {
		double[] r = Arrays2.getArrayDouble(numItems,0.);		
		for(FlowEdge edge:grafo.edgeSet()){
			r[edgeNum(edge)]=grafo.getUnitEdgeWeight(edge);
		}
		for (FlowVertex vertex: grafo.vertexSet()) {
			if (grafo.isSource(vertex) || grafo.isSink(vertex)) {
				r[vertexNum(vertex)] = grafo.getUnitVertexWeight(vertex);
			} else 	if(grafo.getUnitVertexWeight(vertex) != 0.){
				for(FlowEdge edge:grafo.incomingEdgesOf(vertex)){
					r[edgeNum(edge)]=r[edgeNum(edge)]+grafo.getUnitVertexWeight(vertex) ;
				}			
			}
		}
		LinearObjectiveFunction lc = new LinearObjectiveFunction(r,0.);
//		restricciones.add("Funcion Objetivo "+Lists.newArrayList(lc.getCoefficients()));
		return lc;
	}
	
	private LinearConstraint getVertexFlowConstraint(FlowVertex vertex){
		double[] r = Arrays2.getArrayDouble(numItems,0.);		
		for(FlowEdge edge:grafo.incomingEdgesOf(vertex)){
			r[edgeNum(edge)]=1.;
		}
		for(FlowEdge edge:grafo.outgoingEdgesOf(vertex)){
			r[edgeNum(edge)]=-1.;
		}
		if(grafo.isSource(vertex)){
			r[vertexNum(vertex)]=1.;
		}
		if(grafo.isSink(vertex)){
			r[vertexNum(vertex)]=-1.;
		}
		LinearConstraint lc =new LinearConstraint(r,Relationship.EQ,0.);
//		restricciones.add("Restriccion Flow Vertex "+","+vertex+","+Lists.newArrayList(lc.getCoefficients())+","+lc.getValue());
		return lc;
	}
	
	
	private LinearConstraint getVertexEqualConstrainst(FlowVertex vertex, double value){
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		d[vertexNum(vertex)] = 1; // grafo.getMinVertexWeight(vertex);
		LinearConstraint lc = new LinearConstraint(d, Relationship.EQ,value);
//		restricciones.add("Restriccion Equal Vertex "+","+vertex+","+Lists.newArrayList(lc.getCoefficients()+","+lc.getValue()));
		return lc;
	}
		
	
	private LinearConstraint getVertexMinConstrainst(FlowVertex vertex, double value){
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		for(FlowEdge edge:grafo.incomingEdgesOf(vertex)){
			d[edgeNum(edge)] = 1;
		}
		LinearConstraint lc = new LinearConstraint(d, Relationship.GEQ,value);
//		restricciones.add("Restriccion Min Vertex "+","+vertex+","+Lists.newArrayList(lc.getCoefficients()+","+lc.getValue()));
		return lc;
	}
	
	private LinearConstraint getVertexMaxConstrainst(FlowVertex vertex, double value){
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		d[vertexNum(vertex)] = 1; //grafo.getMaxVertexWeight(vertex);
		LinearConstraint lc = new LinearConstraint(d, Relationship.LEQ,value);
//		restricciones.add("Restriccion Max Vertex "+","+vertex+","+Lists.newArrayList(lc.getCoefficients()+","+lc.getValue()));
		return lc;
	}
	
	private LinearConstraint getFlowInVertexMinConstrainst(FlowVertex vertex, double value){
		Preconditions.checkArgument(!grafo.isSource(vertex) && !grafo.isSink(vertex));
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		for(FlowEdge edge:grafo.incomingEdgesOf(vertex)){
			d[edgeNum(edge)] = 1;
		}
		LinearConstraint lc = new LinearConstraint(d, Relationship.GEQ,value);
		return lc;
	}
	
	private LinearConstraint getFlowInVertexMaxConstrainst(FlowVertex vertex, double value){
		Preconditions.checkArgument(!grafo.isSource(vertex) && !grafo.isSink(vertex));
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		for(FlowEdge edge:grafo.incomingEdgesOf(vertex)){
			d[edgeNum(edge)] = 1;
		}
		LinearConstraint lc = new LinearConstraint(d, Relationship.LEQ,value);
		return lc;
	}
	
	private LinearConstraint getFlowInVertexEqualConstrainst(FlowVertex vertex, double value){
		Preconditions.checkArgument(!grafo.isSource(vertex) && !grafo.isSink(vertex));
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		for(FlowEdge edge:grafo.incomingEdgesOf(vertex)){
			d[edgeNum(edge)] = 1;
		}
		if(grafo.isSource(vertex)){
			d[vertexNum(vertex)]=1.;
		}
		LinearConstraint lc = new LinearConstraint(d, Relationship.EQ,value);
		return lc;
	}
	
	private LinearConstraint getEdgeEqualConstrainst(FlowEdge edge,double value){
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		d[edgeNum(edge)] = 1; //grafo.getEdgeWeight(edge);
		LinearConstraint lc = new LinearConstraint(d, Relationship.EQ,value);
//		restricciones.add("Restriccion Eq Edge "+","+edge+","+Lists.newArrayList(lc.getCoefficients()+","+lc.getValue()));
		return lc;
	}
	
	private LinearConstraint getEdgeMinConstrainst(FlowEdge edge,double value){
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		d[edgeNum(edge)] = 1; // grafo.getEdgeWeight(edge);
		LinearConstraint lc = new LinearConstraint(d, Relationship.GEQ,value);
//		restricciones.add("Restriccion Min Edge "+","+edge+","+Lists.newArrayList(lc.getCoefficients()+","+lc.getValue()));
		return lc;
	}
	
	private LinearConstraint getEdgeMaxConstrainst(FlowEdge edge, double value){
		double[] d = Arrays2.getArrayDouble(numItems,0.);
		d[edgeNum(edge)] = 1;   // grafo.getEdgeWeight(edge);
		LinearConstraint lc = new LinearConstraint(d, Relationship.LEQ,value);
//		restricciones.add("Restriccion Max Edge "+","+edge+","+Lists.newArrayList(lc.getCoefficients()+","+lc.getValue()));
		return lc;
	}
	
	static class VertexOrEdge {
		
		public enum Tipo {Vertex,Edge};
		
		Tipo tipo;
		FlowVertex vertex;
		FlowEdge edge;
		
		public VertexOrEdge(Tipo tipo, FlowVertex vertex, FlowEdge edge) {
			super();
			this.tipo = tipo;
			this.vertex = vertex;
			this.edge = edge;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((edge == null) ? 0 : edge.hashCode());
			result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
			result = prime * result
					+ ((vertex == null) ? 0 : vertex.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VertexOrEdge other = (VertexOrEdge) obj;
			if (edge == null) {
				if (other.edge != null)
					return false;
			} else if (!edge.equals(other.edge))
				return false;
			if (tipo != other.tipo)
				return false;
			if (vertex == null) {
				if (other.vertex != null)
					return false;
			} else if (!vertex.equals(other.vertex))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return (tipo.equals(Tipo.Vertex)? vertex.toString():edge.toString());
		}	
		
	}
}
