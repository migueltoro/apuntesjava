package us.lsi.pl;


public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AlgoritmoPLI a = AlgoritmoPLI.create("ejemplo1.txt");
		a.ejecuta();
//		System.out.println(p.toStringConstraints());
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(a.getObjetivo());
		for (int j = 0; j < 5; j++) {
			System.out.println(a.getSolucion()[j]);
		}
		System.out.println("________");
//		System.out.println(s);
	}

}
