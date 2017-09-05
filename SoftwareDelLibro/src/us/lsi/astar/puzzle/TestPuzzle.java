package us.lsi.astar.puzzle;

import us.lsi.astar.puzzle2.EstadoPuzzle2;


public class TestPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EstadoPuzzle2 e = EstadoPuzzle2.create(0,1,2,3,4,5,6,7,8);
		EstadoPuzzle2 e1 = EstadoPuzzle2.create(1,2,3,4,0,5,6,7,8);
		EstadoPuzzle2 e2 = EstadoPuzzle2.create(1,2,3,4,0,5,6,7,8);
		EstadoPuzzle2 e3 = EstadoPuzzle2.create(1,2,3,4,0,5,6,7,8);
		System.out.println(e.equals(e2));
		System.out.println(e.hashCode()+","+e1.hashCode()+","+e2.hashCode()+","+e3.hashCode());
		System.out.println(e);
		System.out.println("Vecinos");
		for (EstadoPuzzle2 v: e.getNeighborListOf()) {
			System.out.println(v+"\n");
		}
	}

}
