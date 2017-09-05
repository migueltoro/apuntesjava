package us.lsi.test;

public class Test {
	public static void mostrar(String p) { 
		System.out.println(p);
	}
	public static <T> void mostrar(T[] a){
		String s = "[";
		for(T e:a){
			s = s+", "+ e;
		}
		s=s+"]";
		System.out.println(s);
	}
	public static void mostrar(Object p) { 
		System.out.println("El objeto es: " +  p);
	}
	public static void mostrar(String s, Object p) {						
		System.out.println(s + p);
	}
	public static void mostrar(String s1, Object[] p){
		String s  = s1+"  [";
		  for(Object o : p){
			  s = s+o;
		  }
		  s= s+"]" ;
        System.out.println(s);
	}
	
	public void assertEquals(Object o1, Object o2){
		if(!o1.equals(o2)){
			mostrar("Ha fallado el test con parámetros "+o1+ "  "+o2);
		}
	}
	
	public void assertEquals(int o1, int o2){
		if(o1!=o2){
			mostrar("Ha fallado el test con parámetros "+o1+ "  "+o2);
		}
	}
	
	public void assertEquals(float o1, float o2){
		if(o1!=o2){
			mostrar("Ha fallado el test con parámetros "+o1+ "  "+o2);
		}
	}
	
	public void assertEquals(double o1, double o2){
		if(o1!=o2){
			mostrar("Ha fallado el test con parámetros "+o1+ "  "+o2);
		}
	}
	
	public void fail(String s){
		mostrar(s);
	}
	    

}
