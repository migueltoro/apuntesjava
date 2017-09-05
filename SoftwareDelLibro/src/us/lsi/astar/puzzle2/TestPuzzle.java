package us.lsi.astar.puzzle2;

import us.lsi.common.PairInteger;
import us.lsi.graphs.ActionSimpleEdge;


public class TestPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EstadoPuzzle2 e = EstadoPuzzle2.create(0,1,2,3,4,5,6,7,8);
		EstadoPuzzle2 e2 = EstadoPuzzle2.create(1,2,3,4,0,5,6,7,8);
		EstadoPuzzle2 e3 = EstadoPuzzle2.create(1,2,3,4,0,5,6,7,8);
		System.out.println(e2.equals(e3));
		System.out.println(e.hashCode()+","+e2.hashCode()+","+e3.hashCode());
		System.out.println(e);
		System.out.println(e.isApplicable(PairInteger.create(1,0)));
		System.out.println("Vecinos");
		for (ActionSimpleEdge<PairInteger, EstadoPuzzle2> ed: e2.edgesOf()) {
			System.out.println(ed+"\n");
		}
		for (EstadoPuzzle2 v: e2.getNeighborListOf()) {
			System.out.println(v+"\n");
		}
	}

}
