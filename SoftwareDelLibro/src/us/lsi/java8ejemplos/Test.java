package us.lsi.java8ejemplos;



import us.lsi.tipos.Fecha;



public class Test {


	public static void main(String[] args) {
//		OtrosEjemplos.ejemplos8();	
		Fecha f1 = Fecha.create(1,1,1990);
		Fecha f2 = Fecha.create(1,1,2010);
		Ejemplos.ejemploQ("fechas.txt", "fechasOut.txt", f1,f2);
//		Stream2.iterate(1, x->Math2.siguientePrimo(x)).whileIncluded(x->x<=1000).forEach(x->System.out.println(x));
//		System.out.println(Math2.esPrimo(29));
	};

}
