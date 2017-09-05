package us.lsi.astar.jarras;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.PairInteger;
import us.lsi.graphs.VirtualVertex;
import us.lsi.pd.jarras.Accion;
import us.lsi.pd.jarras.ProblemaJarrasPD;

public class VertexJarras implements VirtualVertex<VertexJarras, EdgeJarras<VertexJarras>> {

	
	
	public static VertexJarras create(Integer cantidadEnJ1, Integer cantidadEnJ2) {
		return new VertexJarras(cantidadEnJ1, cantidadEnJ2);
	}

	public static VertexJarras createOrigen() {
		return new VertexJarras(0, 0);
	}
	
	public static VertexJarras createDestino() {
		return new VertexJarras(ProblemaJarrasPD.cantidadFinalEnJarra1, ProblemaJarrasPD.cantidadFinalEnJarra2);
	}
	
	private Integer cantidadEnJ1;
	private Integer cantidadEnJ2;
	private Set<EdgeJarras<VertexJarras>>  edges = null;
	private Set<VertexJarras> neighbors = null;

	private VertexJarras(Integer cantidadEnJ1, Integer cantidadEnJ2) {
		super();
		this.cantidadEnJ1 = cantidadEnJ1;
		this.cantidadEnJ2 = cantidadEnJ2;
	}
	
	

	public Integer getCantidadEnJ1() {
		return cantidadEnJ1;
	}

	public Integer getCantidadEnJ2() {
		return cantidadEnJ2;
	}

	@Override
	public boolean isValid() {
		return this.cantidadEnJ1>=0 && this.cantidadEnJ2>=0;
	}

	@Override
	public Set<VertexJarras> getNeighborListOf() {
		if (this.neighbors == null) {
			this.edgesOf().stream().map(x -> x.getTarget())
					.collect(Collectors.toSet());
		}
		return this.neighbors;
	}

	@Override
	public Set<EdgeJarras<VertexJarras>> edgesOf() {
		if (this.edges == null) {
			this.edges = IntStream
					.range(0, Accion.acciones.size())
					.boxed()
					.map(x -> Accion.acciones.get(x))
					.filter(x -> x.isAplicable(this.cantidadEnJ1,
							this.cantidadEnJ2))
					.map(x -> EdgeJarras.create(this, this.next(x), x))
					.collect(Collectors.toSet());
		}
		return this.edges;
	}
	
	public VertexJarras next(Accion a){
		PairInteger r = a.ejecuta(PairInteger.create(this.cantidadEnJ1,this.cantidadEnJ2));	
		return VertexJarras.create(r.v1,r.v2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cantidadEnJ1 == null) ? 0 : cantidadEnJ1.hashCode());
		result = prime * result
				+ ((cantidadEnJ2 == null) ? 0 : cantidadEnJ2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VertexJarras))
			return false;
		VertexJarras other = (VertexJarras) obj;
		if (cantidadEnJ1 == null) {
			if (other.cantidadEnJ1 != null)
				return false;
		} else if (!cantidadEnJ1.equals(other.cantidadEnJ1))
			return false;
		if (cantidadEnJ2 == null) {
			if (other.cantidadEnJ2 != null)
				return false;
		} else if (!cantidadEnJ2.equals(other.cantidadEnJ2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[J1=" + cantidadEnJ1 + ", J2="
				+ cantidadEnJ2 + "]";
	}

	
}
