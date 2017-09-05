package us.lsi.dyv.ejemplos;

import java.util.Comparator;
import java.util.List;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.dyv.AlgoritmoDyVSM;
import us.lsi.math.Math2;

public class SorTest {

	public static void main(String[] args) {
		List<Double> ls = Math2.getListDoubleAleatoria(50, -20., 20.);
		System.out.println(ls);
		Comparator<Double> ord = Comparator.naturalOrder();
		AlgoritmoDyVSM<Void,Void> a = Algoritmos.createDyVSM(SortList.create(ls,ord));
		a.ejecuta();
		System.out.println(ls);
		
	}

}
