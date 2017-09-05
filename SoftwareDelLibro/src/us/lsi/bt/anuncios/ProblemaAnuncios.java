package us.lsi.bt.anuncios;

import java.util.*;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.lsi.common.PairInteger;
import us.lsi.common.Sets2;
import us.lsi.stream.Stream2;

public class ProblemaAnuncios {

	public static List<Anuncio> todosLosAnunciosDisponibles;
	public static Integer tiempoTotal;
	public static Set<PairInteger> restricciones;
	public static Set<Integer> todosLosAnuncios; 
	
	public ProblemaAnuncios() {
		super();
	}
	
	public static void leeYOrdenaAnuncios(String file){	
		List<String> ls = Stream2.fromFile(file).toList();
		int index = ls.indexOf("#");
		List<String> ls1 = ls.subList(0, index);
		List<String> ls2 = ls.subList(index+1, ls.size());
		todosLosAnunciosDisponibles = Lists.newArrayList();
		Anuncio a;
		for(String s : ls1){
			String[] at = Stream2.fromString(s, ",").<String>toArray((int x)->new String[x]);
			Preconditions.checkArgument(at.length==3);
			a = Anuncio.create(at);
			todosLosAnunciosDisponibles.add(a);
		}
		restricciones = new HashSet<>();
		for(String s : ls2){
			String[] at = Stream2.fromString(s, ",").<String>toArray((int e)->new String[e]);
			Preconditions.checkArgument(at.length==2);
			Integer n1 = new Integer(at[0]);
			Integer n2 = new Integer(at[1]);
			restricciones.add(PairInteger.create(n1, n2));
			restricciones.add(PairInteger.create(n2, n1));
		}
		Collections.sort(ProblemaAnuncios.todosLosAnunciosDisponibles, Comparator.<Anuncio>naturalOrder().reversed());
		todosLosAnuncios = Sets2.newSet(0, ProblemaAnuncios.todosLosAnunciosDisponibles.size());
	}

	public static ProblemaAnuncios create() {		
		return new ProblemaAnuncios();
	}

	public static Anuncio getAnuncio(int i){
		return todosLosAnunciosDisponibles.get(i);
	}
	
}
