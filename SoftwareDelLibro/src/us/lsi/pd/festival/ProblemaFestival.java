package us.lsi.pd.festival;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.common.base.Preconditions;

import us.lsi.stream.Stream2;

public class ProblemaFestival {

	public static int actuaciones;
	public static Map<String, Map<String, List<Grupo>>> gruposPorDiaYHora; 
	public static List<List<Grupo>> gruposPorFranja; 
	public static Integer presupuestoTotal;
	static public List<String> diaYHora;
	
	public static ProblemaFestival create(String file) {		
		return new ProblemaFestival(file);
	}

	public ProblemaFestival(String file) {
		super();
		leeGrupos(file);
		inicia();
	}
	
	public static void leeGrupos(String file){	
		List<String> ls = Stream2.fromFile(file).toList();
		ProblemaFestival.gruposPorDiaYHora = new LinkedHashMap<String, Map<String, List<Grupo>>>();
		ProblemaFestival.actuaciones = 0;
		for(String s : ls){
			String[] at = Stream2.fromString(s, ",").<String>toArray((int x)->new String[x]);
			Preconditions.checkArgument(at.length==7);
			Grupo a = Grupo.create(actuaciones, at);
			if (!ProblemaFestival.gruposPorDiaYHora.containsKey(a.getDia())){
				ProblemaFestival.gruposPorDiaYHora.put(a.getDia(), new LinkedHashMap<String, List<Grupo>>());
			}
			if (!ProblemaFestival.gruposPorDiaYHora.get(a.getDia()).containsKey(a.getHora())){
				ProblemaFestival.gruposPorDiaYHora.get(a.getDia()).put(a.getHora(), new ArrayList<Grupo>());
			}
			ProblemaFestival.gruposPorDiaYHora.get(a.getDia()).get(a.getHora()).add(a);
			actuaciones++;
		}
	}

	public static void inicia(){
		ProblemaFestival.diaYHora = new ArrayList<String>();
		ProblemaFestival.gruposPorFranja = new ArrayList<List<Grupo>>();
		for (String dia: ProblemaFestival.gruposPorDiaYHora.keySet()){
			for(String hora: ProblemaFestival.gruposPorDiaYHora.get(dia).keySet()){
				ProblemaFestival.diaYHora.add(dia +"_"+ hora);
				ProblemaFestival.gruposPorFranja.add(new ArrayList<Grupo>(ProblemaFestival.gruposPorDiaYHora.get(dia).get(hora)));
			}
		}
	}

}

