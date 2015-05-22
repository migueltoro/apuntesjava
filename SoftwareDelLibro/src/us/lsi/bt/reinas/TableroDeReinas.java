package us.lsi.bt.reinas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.common.Lists2;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class TableroDeReinas  {

	public static TableroDeReinas create() {
		return new TableroDeReinas();
	}

	public static TableroDeReinas create(Integer... ls) {
		return new TableroDeReinas(ls);
	}

	public static TableroDeReinas create(List<Integer> ls) {
		return new TableroDeReinas(ls);
	}
	
	public static TableroDeReinas create(TableroDeReinas t) {
		return new TableroDeReinas(t);
	}
	
	private List<Integer> yOcupadas;
	private Set<Integer> diagonalesPrincipalesOcupadas;
	private Set<Integer> diagonalesSecundariasOcupadas;
	private Integer x;
	private List<Integer> yDisponibles;
	
	
	private double objetivo; 
			
	private TableroDeReinas() {
		this(new ArrayList<Integer>());		
	}
	
	private TableroDeReinas(Integer... ls) {
		this(Arrays.asList(ls));
	}
	
	private TableroDeReinas(List<Integer> ls) {
		this.yOcupadas = new ArrayList<>(ls);		
		this.calculaPropiedadesDerivadas();
	}
		
	private TableroDeReinas(TableroDeReinas t) {
		this(t.yOcupadas);		
	}
		
	private void calculaPropiedadesDerivadas(){
		this.x = this.getNumDeReinas();
		this.diagonalesPrincipalesOcupadas = new HashSet<>();
		this.diagonalesSecundariasOcupadas = new HashSet<>();
		for(int i=0; i < this.yOcupadas.size();i++){
			Reina r = Reina.create(i, this.yOcupadas.get(i));
			this.diagonalesPrincipalesOcupadas.add(r.getDiagonalPrincipal());
			this.diagonalesSecundariasOcupadas.add(r.getDiagonalSecundaria());
		}
		this.objetivo = 2*getNumDeReinas()
						-this.diagonalesPrincipalesOcupadas.size()
						-this.diagonalesSecundariasOcupadas.size();	
		this.yDisponibles = getDisponibles();
	}
	
	private boolean libre(int y){
		Reina r = Reina.create(this.x, y);
		return !this.getDiagonalesPrincipalesOcupadas()
					.contains(r.getDiagonalPrincipal())
				&& !this.getDiagonalesSecundariasOcupadas()
						.contains(r.getDiagonalSecundaria())
				&& !this.getYOcupadas().contains(r.getY());
	}
	
	private List<Integer> getDisponibles() {
		Stream<Integer> s = IntStream
				.range(0, ProblemaReinasBT.numeroDeReinas)
				.filter(y -> libre(y))
				.boxed();
		return s.collect(Collectors.toList());
	}
	
	public double getObjetivo() {
		return objetivo;
	}	
	
	public TableroDeReinas add(Integer y){
		List<Integer> ls = new ArrayList<Integer>(this.yOcupadas);
		ls.add(y);
		return create(ls);	
	}
	
	public TableroDeReinas remove(){
		List<Integer> ls = new ArrayList<Integer>(this.yOcupadas);
		int lastIndex = this.yOcupadas.size()-1;
		ls.remove(lastIndex);
		return create(ls);	
	}
	
	public Integer last(){
		Preconditions.checkArgument(!this.yOcupadas.isEmpty());
		return this.yOcupadas.get(this.yOcupadas.size()-1);
	}
	
	public TableroDeReinas intercambia(int x1, int x2){
		int size = this.yOcupadas.size();
		Preconditions.checkPositionIndex(x1, size);
		Preconditions.checkPositionIndex(x2, size);
		List<Integer> ls = new ArrayList<Integer>(this.yOcupadas);
		Lists2.intercambia(ls,x1,x2);
		return create(ls);
	}
	
	public List<Reina> getReinas(){
		List<Reina> ls = new ArrayList<>();
		for(int i=0; i < this.yOcupadas.size(); i++){
			Reina r = Reina.create(i, this.yOcupadas.get(i));
			ls.add(r);
		}
		return ls;
	}
	
	public int getNumDeReinas(){
		return this.yOcupadas.size();
	}	
	
	public List<Integer> getYOcupadas() {
		return yOcupadas;
	}

	public Set<Integer> getDiagonalesPrincipalesOcupadas() {
		return diagonalesPrincipalesOcupadas;
	}

	public Set<Integer> getDiagonalesSecundariasOcupadas() {
		return diagonalesSecundariasOcupadas;
	}	
	
	public List<Integer> getYDisponibles() {
		return yDisponibles;
	}
	
	public Integer getX() {
		return x;
	}

	public String toString(){
		return "";
	}
	
	public TableroDeReinas copia() {
		return create(this);
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((yOcupadas == null) ? 0 : yOcupadas.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TableroDeReinas))
			return false;
		TableroDeReinas other = (TableroDeReinas) obj;
		if (yOcupadas == null) {
			if (other.yOcupadas != null)
				return false;
		} else if (!yOcupadas.equals(other.yOcupadas))
			return false;
		return true;
	}

	public static void main(String[] args) {
		TableroDeReinas t = TableroDeReinas.create(0, 1, 2, 3, 4, 5, 6, 7);
		System.out.println(t.getNumDeReinas());
		System.out.println(t.getYOcupadas());
		System.out.println(t.getDiagonalesPrincipalesOcupadas());
		System.out.println(t.getDiagonalesSecundariasOcupadas());
/*		t.add(8);
		System.out.println(t.getNumDeReinas());
		System.out.println(t.getNumY());
		System.out.println(t.getNumDiagonalesPrincipalesOcupadas());
		System.out.println(t.getNumDiagonalesSecundariasOcupadas());
		t.remove();;
		System.out.println(t.getNumDeReinas());
		System.out.println(t.getNumY());
		System.out.println(t.getNumDiagonalesPrincipalesOcupadas());
		System.out.println(t.getNumDiagonalesSecundariasOcupadas());
*/		TableroDeReinas t2 = t.intercambia(0, 1);
		System.out.println(t2.getNumDeReinas());
		System.out.println(t2.getObjetivo());
		System.out.println(t2.getYOcupadas());
		System.out.println(t2.getDiagonalesPrincipalesOcupadas());
		System.out.println(t2.getDiagonalesSecundariasOcupadas());
		List<Integer> ls = Lists.newArrayList(0,1,2,3,4,5);
		Lists2.intercambia(ls, 0,4);
		System.out.println(ls);
		
	}

	
	
	

}
