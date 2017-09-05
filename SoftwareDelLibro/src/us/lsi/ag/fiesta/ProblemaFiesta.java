package us.lsi.ag.fiesta;


import java.util.List;


import us.lsi.common.Tuple2;

public class ProblemaFiesta {
	public static List<Actividad> actividadesDisponibles;
	public static List<Tuple2<Actividad,Actividad>> restricciones;
	public static Double presupuestoTotal;
	
	public static Actividad getActividad(Integer i){
		return ProblemaFiesta.actividadesDisponibles.get(i);
	}

//  Una solución alternativa usando un Map
//	private static Map<Actividad,Integer> pos;	
	
	public static Integer getPos(Actividad a){
		return a.getId();
//		return pos.get(a);
	}

	public static void setPos(){		
		for (int i = 0; i < ProblemaFiesta.actividadesDisponibles.size(); i++) {
			ProblemaFiesta.actividadesDisponibles.get(i).setId(i);;
		}
//		pos = new HashMap<>();		
//		for (int i = 0; i < ProblemaFiesta.actividadesDisponibles.size(); i++) {
//			pos.put(getActividad(i),i);;
//		}
	}
}
