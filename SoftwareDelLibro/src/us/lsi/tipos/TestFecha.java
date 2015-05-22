package us.lsi.tipos;



public class TestFecha {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fecha f1 = Fecha.create(8,10,2009);
		Fecha f1a = Fecha.create(7,10,2009);
		Fecha f1b = Fecha.create(1,9,2009);
		Fecha f2 = Fecha.create(1, 1, 2000);	
		Fecha f3 = Fecha.create(1, 3, 2009);	
		Fecha f4 = Fecha.create(1,1,1900);
		Fecha f5 = Fecha.create(2,3,2011);
		System.out.println("f1="+f1+","+"f2="+f2+","+"f3="+f3+","+"f4="+f4+","+"f5="+f5);
		
		System.out.println(f1.resta(f1a));
		System.out.println(f1.resta(f1b));
		System.out.println(f2.esBisiesto());
		Fecha f6 = Fecha.create(2, 2, 2000);
		System.out.println(f6);
		Fecha f7 = Fecha.create("31/9/2013");
		System.out.println(f7);
	}

}
