package us.lsi.astar.puzzle2;

import us.lsi.common.ParInteger;


public class TestPuzzle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EstadoPuzzle e = EstadoPuzzle.create(0,1,2,3,4,5,6,7,8);
		EstadoPuzzle e2 = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
		EstadoPuzzle e3 = EstadoPuzzle.create(1,2,3,4,0,5,6,7,8);
		System.out.println(e.equals(e2));
		System.out.println(e.hashCode()+","+e2.hashCode()+","+e3.hashCode());
		System.out.println(e);
		System.out.println(e.isApplicable(ParInteger.create(1,0)));
		System.out.println(e.neighbor(ParInteger.create(1,0)));
		for (EstadoPuzzle v: e2.getNeighborListOf()) {
			System.out.println(v+"\n");
		}
	}

}
