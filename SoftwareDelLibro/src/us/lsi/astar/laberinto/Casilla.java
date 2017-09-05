package us.lsi.astar.laberinto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import us.lsi.common.PairInteger;
import us.lsi.graphs.*;
import us.lsi.stream.Stream2;

public class Casilla implements VirtualVertex<Casilla,SimpleEdge<Casilla>> {
	private Integer x;
	private Integer y;
	private Integer info;
	public static int numX;
	public static int numY;
	public static Table<Integer,Integer,Integer> datosCasillas = HashBasedTable.<Integer,Integer,Integer>create();
	
	private Casilla(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.info = datosCasillas.get(y, y);
		if(!isValid()){
			throw new IllegalArgumentException("Casilla no valida con "+this.x+","+this.y+","+this.info);
		}
	}
	
	public Integer getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Integer getInfo() {
		return info;
	}

	public static void iniDatos(String nf, int nx, int ny){
		numX = nx;
		numY = ny;
		Stream<Integer> ls = Stream2.fromFile(nf)
				.<String>flatMap((String x)->Stream2.fromString(x, ","))
				.<Integer>map((String x)->new Integer(x));
		
		
		Container ct = new Container(Casilla.datosCasillas);
		
		ls.forEach((Integer e)->{ ct.add(e); });
				
		if(ct.numDatos!= numX*numY){
			throw new IllegalArgumentException("No hay el número adecuado de datos");
		}
	}

	
	public static Casilla create(int x, int y) {
		return new Casilla(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Casilla other = (Casilla) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + info + ")";
	}

	@Override
	public Set<Casilla> getNeighborListOf() {
		List<PairInteger> ls = Lists.newArrayList(PairInteger.create(1,0),
				PairInteger.create(0,1),PairInteger.create(-1,0),PairInteger.create(0,-1));
		return ls.stream()
				 .<PairInteger> map((PairInteger x)-> PairInteger.create(x.v1+this.x, x.v2+this.y))
				 .filter((PairInteger x)-> x.v1>=0 && x.v1 < numX && x.v2>=0 && x.v2 < numY && 
				 		  datosCasillas.get(x.v1, x.v2) >=0)
				 .<Casilla>map((PairInteger x)-> Casilla.create(x.v1,x.v2))
				 .collect(Collectors.<Casilla>toSet());
	}

	@Override
	public Set<SimpleEdge<Casilla>> edgesOf() {
		return getNeighborListOf().stream()
				.map(c->SimpleEdge.create(this,c))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean isNeighbor(Casilla e) {
		return Math.abs(this.getX()-e.getX()) + Math.abs(this.getY()-e.getY()) == 1;
	}
	
	@Override
	public boolean isValid() {
		return this.info >=0 && this.getX() >=0 && this.getX()< numX && this.getY() >=0 && this.getY()< numY;
	}
	
	private static String normalize(String s, int n){
		int t = n-s.length();
		String r = "";
		for(int i =0;i<t;i++){
			r= r+" ";
		}	
		r = r+s;
		return r;
	}
	
	public static String show(GraphPath<Casilla,SimpleEdge<Casilla>> p){
		List<Casilla> lc = p.getVertexList();		
		String s = "";
		Casilla c;
		for(int y = Casilla.numY-1; y>=0; y--){
			for(int x=0; x < Casilla.numX;x++){
					c = Casilla.create(x, y);
					if(lc.contains(c)){
						s = s+normalize("X",7);
					}else{
						s = s+normalize(Casilla.datosCasillas.get(x, y).toString(),7);
					}
			}
			s = s+"\n";
		}
	    return s;
	}
	
	private static class Container {
		private Table<Integer,Integer,Integer> datosCasillas;
		private int ax = 0;
		private int ay = numY-1;
		public int numDatos;
			
		public Container(Table<Integer, Integer, Integer> datosCasillas) {
			super();
			this.datosCasillas = datosCasillas;
			this.ax = 0;
			this.ay = numY-1;
			this.numDatos = 0;
		}


		public void add(Integer e){
			this.datosCasillas.put(ax, ay, e) ;
			numDatos++;
			if(ax==Casilla.numX){
				ax=0;
				ay--;
			}else{
				ax++;
			}
		}
	}

	
	
}
