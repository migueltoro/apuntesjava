package us.lsi.astar.puzzle2;

import java.util.*;

import com.google.common.collect.Lists;

import us.lsi.common.*;
import us.lsi.graphs.AlternativeVirtualVertex;

/**
 * 
 * * Estado de un Puzzle como implementación de AlternativeVirtualVertex. Esta versión simplifica el código con repecto 
 * a la a laversión que implementa VirtualVertex directamente.
 * 
 * @author Miguel Toro
 *
 */
public class EstadoPuzzle extends AlternativeVirtualVertex<ParInteger,EstadoPuzzle> {
	
	/**
	 * @param d Lista de valores del puzzle dados por filas de abajo arriba
	 * @return Un EstadoPuzzle
	 */
	public static EstadoPuzzle create(Integer... d) {
		return new EstadoPuzzle(d);
	}
	
	private static List<ParInteger> values = null;
	
	private Integer[][] datos; 
	private int i0;
	private int j0;
	public static int numFilas = 3;
	
	private EstadoPuzzle(Integer... d) {
		super(); 
		this.datos = new Integer[EstadoPuzzle.numFilas][EstadoPuzzle.numFilas];
		Set<Integer> s = new HashSet<Integer>();	
		int i=0,j=0;
		for (int e:d) {							
					if(e<0 || e > (EstadoPuzzle.numFilas*EstadoPuzzle.numFilas-1)){
						throw new IllegalArgumentException();
					}
					if(e==0){
						this.i0 =i;
						this.j0 =j;
					}
					s.add(e);
					this.datos[i][j] = e;
					if(i== EstadoPuzzle.numFilas-1){
						j++;
						i=0;
					}else{
						i++;
					}
				
		}
		if(d.length!= EstadoPuzzle.numFilas*EstadoPuzzle.numFilas || s.size()!= EstadoPuzzle.numFilas*EstadoPuzzle.numFilas)
			throw new IllegalArgumentException();
	}


	public EstadoPuzzle(Integer[][] datos, int i0, int j0) {
		super();
		this.datos = datos;
		this.i0 = i0;
		this.j0 = j0;
	}

	public Integer getDato(int i, int j) {
		if(i<0 || i>=EstadoPuzzle.numFilas || j<0 || j>=EstadoPuzzle.numFilas)
			throw new IllegalArgumentException();
		return datos[i][j];
	}
	
	@Override
	public boolean isNeighbor(EstadoPuzzle e) {
		return this.getNumDiferentes(e)==2 && Math.abs(this.i0-e.i0)+Math.abs(this.j0-e.j0)==1;
	}
	
	@Override
	public boolean isValid() {
		Set<Integer> s = new HashSet<Integer>();	
		for (int j = 0; j < EstadoPuzzle.numFilas; j++) {
			for (int i = 0; i < EstadoPuzzle.numFilas; i++) {
				s.add(datos[i][j]);
			}
		}
		return s.size()== EstadoPuzzle.numFilas*EstadoPuzzle.numFilas;
	}
	
	public Integer getNumDiferentes(EstadoPuzzle e){
		Integer s = 0;
		for (int i = 0; i < EstadoPuzzle.numFilas; i++) {
			for (int j = 0; j < EstadoPuzzle.numFilas; j++) {
				if (!this.datos[i][j].equals(e.datos[i][j])) {
					s++;
				}
			}
		}
		return s;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(datos);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EstadoPuzzle))
			return false;
		EstadoPuzzle other = (EstadoPuzzle) obj;
		if (!Arrays.deepEquals(datos, other.datos))
			return false;
		return true;
	}


	@Override
	public String toString() {
		String s ="\n";
		
		for (int j = datos.length-1; j >= 0; j--) {
			for (int i = 0; i < datos.length; i++) {
				if (i<datos.length-1) {
					s = s + datos[i][j]+",";
				}else {
					s = s + datos[i][j]+"\n";
				}
			}
		}
		return s;
	}


	@Override
	public EstadoPuzzle neighbor(ParInteger p) {
		if(!isApplicable(p))
			throw new IllegalArgumentException();
		int i0 = this.i0 + p.p1;
		int j0 = this.j0 + p.p2;
		Integer[][] dd = new Integer[EstadoPuzzle.numFilas][EstadoPuzzle.numFilas];
		for (int i = 0; i < EstadoPuzzle.numFilas; i++) {
			for (int j = 0; j < EstadoPuzzle.numFilas; j++) {
				dd[i][j] = datos[i][j];
			}
		}
		dd[i0][j0] = datos[this.i0][this.j0];
		dd[this.i0][this.j0] = datos[i0][j0];
		return new EstadoPuzzle(dd,i0,j0);
	}


	@Override
	public boolean isApplicable(ParInteger p) {
		return p.p1+this.i0>=0 && p.p1+this.i0<EstadoPuzzle.numFilas && 
				p.p2+this.j0>=0 && p.p2+this.j0<EstadoPuzzle.numFilas;
	}


	@Override
	protected List<ParInteger> values() {
		if(EstadoPuzzle.values==null){
			EstadoPuzzle.values = Lists.newArrayList(ParInteger.create(1,0),ParInteger.create(0,1),ParInteger.create(-1,0),ParInteger.create(0,-1));
		}
		return EstadoPuzzle.values;
	}


	@Override
	protected EstadoPuzzle getThis() {
		return this;
	}
	
	
}
