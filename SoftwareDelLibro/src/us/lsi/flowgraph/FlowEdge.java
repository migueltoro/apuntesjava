package us.lsi.flowgraph;
import org.jgraph.graph.DefaultEdge;

/**
 * Una arista simple de una Red de Fujo.
 * Cada arista de este tipo tiene asociado un coste unitario, un flujo máximo y otro mínimo 
 * 
 * @author Miguel Toro
 *
 */
public class FlowEdge extends DefaultEdge {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Double min=0.;
	private Double max=Double.MAX_VALUE;
	private Double cost=0.;
	
	public static FlowEdge createEdge(FlowVertex v1, FlowVertex v2, String[] formato) {
		return new FlowEdge(v1,v2,formato);
	}
	
	public static FlowEdge createEdge(FlowVertex v1, FlowVertex v2) {
		return new FlowEdge(v1,v2);
	}
	
	private FlowEdge(FlowVertex from, FlowVertex to, Double min, Double max,Double cost)  {
		super();
		super.source=from;
		super.target =to;
		this.min = min;
		this.max = max;
		this.cost = cost;
	}
	
	private FlowEdge(FlowVertex from, FlowVertex to) {
		super();
		super.source=from;
		super.target =to;
		this.min = 0.;
		this.max = Double.MAX_VALUE;
		this.cost = 0.;
	}
	
	private FlowEdge(FlowVertex from, FlowVertex to,String[] formato) {
		super();
		super.source=from;
		super.target =to;
		this.min = formato[2].equals("inf")?Double.MAX_VALUE:new Double(formato[2]);
		this.max = formato[3].equals("inf")?Double.MAX_VALUE:new Double(formato[3]);
		this.cost = formato[4].equals("inf")?Double.MAX_VALUE:new Double(formato[4]);
	}
	
	public Double getMin() {
		return min;
	}

	public Double getMax() {
		return max;
	}

	public Double getCost() {
		return cost;
	}
	
	public FlowVertex getFrom() {
		return (FlowVertex) super.source;
	}

	public FlowVertex getTo() {
		return (FlowVertex) super.target;
	}
	
	@Override
	public String toString() {
		return source.toString() + "--" + target.toString();
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof FlowEdge))
			return false;
		FlowEdge other = (FlowEdge) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	

}
