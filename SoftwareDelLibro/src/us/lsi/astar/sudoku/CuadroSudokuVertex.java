package us.lsi.astar.sudoku;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.VirtualVertex;
import us.lsi.sa.sudoku.CuadroSudoku;

public class CuadroSudokuVertex implements VirtualVertex<CuadroSudokuVertex, SimpleEdge<CuadroSudokuVertex>> {
	
	public static CuadroSudokuVertex create(List<Integer> casillasOcupadas) {
		return new CuadroSudokuVertex(casillasOcupadas);
	}

	public static CuadroSudokuVertex create() {
		return new CuadroSudokuVertex(Lists.newArrayList());
	}
	
	private List<Integer> casillasOcupadas;
	private Integer pos;
	private CuadroSudoku cuadro;
	
	private CuadroSudokuVertex(List<Integer> casillasOcupadas) {
		super();
		this.casillasOcupadas = casillasOcupadas;
		this.pos = casillasOcupadas.size();
		this.cuadro = CuadroSudoku.create(casillasOcupadas);
	}
	
	public List<Integer> getValoresEnCasillasOcupadas() {
		return casillasOcupadas;
	}

	public Integer getPos() {
		return pos;
	}

	public CuadroSudoku getCuadro() {
		return cuadro;
	}
	
	public CuadroSudokuVertex add(Integer a){
		List<Integer> ls = Lists.newArrayList(this.casillasOcupadas);
		ls.add(a);
		return CuadroSudokuVertex.create(ls);
	}

	@Override
	public boolean isValid() {
		return true;
	}


	@Override
	public Set<CuadroSudokuVertex> getNeighborListOf() {
		if(this.pos >= CuadroSudoku.numPosicionesLibres) return Sets.newHashSet();
		Set<Integer> s = this.getCuadro().getValoresLibresEnPos(CuadroSudoku.posicionesLibres.get(this.pos));		
		Set<CuadroSudokuVertex> ss = s.stream().map(x->this.add(x)).collect(Collectors.toSet());
		return ss;
	}


	@Override
	public Set<SimpleEdge<CuadroSudokuVertex>> edgesOf() {
		return getNeighborListOf().stream()
				.map(x->SimpleEdge.create(this, x))
				.collect(Collectors.toSet());
	}


	@Override
	public boolean isNeighbor(CuadroSudokuVertex e) {
		return getNeighborListOf().contains(e);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuadro == null) ? 0 : cuadro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CuadroSudokuVertex))
			return false;
		CuadroSudokuVertex other = (CuadroSudokuVertex) obj;
		if (cuadro == null) {
			if (other.cuadro != null)
				return false;
		} else if (!cuadro.equals(other.cuadro))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return pos+","+cuadro.getObjetivoMin()+","+casillasOcupadas;
	}	

}
