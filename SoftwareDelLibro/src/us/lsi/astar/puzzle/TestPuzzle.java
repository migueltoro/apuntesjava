package us.lsi.astar.puzzle;

import us.lsi.graphs.SimpleEdge;

public class TestPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EstadoPuzzle e = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
		EstadoPuzzle e2 = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
		EstadoPuzzle e3 = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
		System.out.println(e.equals(e2));
		System.out.println(e.hashCode()+","+e2.hashCode()+","+e3.hashCode());
		for (SimpleEdge<EstadoPuzzle> v: e.edgesOf()) {
			System.out.println(v+"\n");
		}
	}

}
