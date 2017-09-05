package us.lsi.flowgraph;
import us.lsi.flowgraph.FlowGraph.TipoDeVertice;

/**
 * Un vértice de una Red de Fujo.
 * Un vértice de este tipo tiene asociado un coste unitario, un flujo máximo, otro mínimo 
 * y el tipo de vértice
 * @see us.lsi.pl.ProblemaPL.TipoDeOptimizacion 
 * 
 * @author Miguel Toro
 *
 */
public class FlowVertex {
	private String id;	
	private TipoDeVertice tipo;
	private Double min=0.;
	private Double max=Double.MAX_VALUE;
	private Double cost=0.;

	public static FlowVertex create(String[] formato) {
		return new FlowVertex(formato);
	}
	
	public static FlowVertex create(String id) {
		return new FlowVertex(id);
	}

	private FlowVertex(String id) {
		super();
		this.id = id;
		this.tipo = TipoDeVertice.Intermedio;
		this.min = 0.;
		this.max = Double.MAX_VALUE;
		this.cost = 0.;
	}
	
	private FlowVertex(String nombre, Integer tipo, Double min, Double max,Double cost) {
		super();
		this.id = nombre;
		switch(tipo){
		case 0: this.tipo = TipoDeVertice.Intermedio; break;
		case 1: this.tipo = TipoDeVertice.Source; break;
		case 2: this.tipo = TipoDeVertice.Sink; break;
		default: throw new IllegalArgumentException("Valor no adecuado para el tipo de un vértice");		
		}
		
		this.min = min;
		this.max = max;
		this.cost = cost;
	}
	
	private FlowVertex(String[] formato) {
		super();
		this.id = formato[0];
		Integer tipo = new Integer(formato[1]);
		switch(tipo){
		case 0: this.tipo = TipoDeVertice.Intermedio; break;
		case 1: this.tipo = TipoDeVertice.Source; break;
		case 2: this.tipo = TipoDeVertice.Sink; break;
		default: throw new IllegalArgumentException("Valor no adecuado para el tipo de un vértice");		
		}
		if (formato.length==5) {
			this.min = formato[2].equals("inf") ? Double.MAX_VALUE : new Double(
					formato[2]);
			this.max = formato[3].equals("inf") ? Double.MAX_VALUE : new Double(
					formato[3]);
			this.cost = formato[4].equals("inf") ? Double.MAX_VALUE : new Double(
					formato[4]);
		}
	}

	public String getId() {
		return id;
	}

	public TipoDeVertice getTipo() {
		return tipo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FlowVertex other = (FlowVertex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id;
	}
	
}
