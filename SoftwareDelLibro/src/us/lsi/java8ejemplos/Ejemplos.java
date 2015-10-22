package us.lsi.java8ejemplos;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;




import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import us.lsi.geometria.Punto2D;
import us.lsi.math.Math2;
import us.lsi.stream.Stream2;
import us.lsi.stream.Collectors2;
import us.lsi.tipos.Fecha;
import us.lsi.common.Entry;

/**
*
*
* @author Miguel Toro
*/

public class Ejemplos {

	public static <T> List<T> ejemploA(Stream<T> st){
		return st.collect(Collectors.<T>toList());
	}
	
	public static List<Double> ejemploB(List<Punto2D> ls){
		return ls.stream().map(x->x.getX()).collect(Collectors.toList());
	}
	
	public static boolean ejemploC(List<Integer> ls){
		return ls.stream().allMatch(x->x%2==1);
	}
	
	public static boolean ejemploD(List<Integer> ls){
		return ls.stream().anyMatch(x->x%2==1 && Math2.esPrimo(x));
	}
	
	public static Double ejemploE(List<Double> ls){
		return ls.stream().mapToDouble(x->x).sum();
	}
	
	public static Integer ejemploF(List<Integer> ls){
		return ls.stream().mapToInt(x->x*x).sum();
	}
	
	public static Double ejemploG(List<Double> ls, Double umbral){
		return ls.stream().filter(x-> umbral > x).findFirst().get();
	}
	
	public static List<Punto2D> ejemploH(List<Punto2D> ls){
		return ls.stream().map(x->Punto2D.create(-x.getX(),x.getY())).collect(Collectors.toList());
	}
	
	public static Punto2D ejemploI(List<Punto2D> ls){
		return ls.stream().min(Comparator.comparing(Punto2D::getX)).get();
	}
	
	public static Long ejemploJ(List<Punto2D> ls){
		return ls.stream().filter(x->x.getX()>=0. && x.getY()>=0.).count();
		
	}
	
	public static List<Punto2D> ejemploK(Punto2D[] ls){
		return Arrays.stream(ls).collect(Collectors.toList());
	}
	
	public static void ejemploL(String fileOut, Integer limit){
		Stream2.iterate(1, x->Math2.siguientePrimo(x)).whileIncluded(x-> x<=limit).mapToStr(x->x.toString()).toFile(fileOut);
	}
	
	public static Stream<Integer> ejemploM(Stream<List<Integer>> st){
		return st.flatMap(x->x.stream());
	}
	
	public static void ejemploN(String fileOut, Integer limit){
		Stream2.iterate(1, x->Math2.siguientePrimo(x)).whileIncluded(x-> x<=limit).map(x->x*x).mapToStr(x->x.toString()).toFile(fileOut);
	}
	
	public static IntStream ejemploO(String fileIn){
		return Stream2.fromFile(fileIn).mapToInt(x-> new Integer(x));
	}
	
	public static Stream<Punto2D> ejemploP(Integer limit){
		return Stream2.iterate(1L, x->Math2.siguientePrimo(x)).whileIncluded(x-> x<=limit).<Punto2D>map(x->Punto2D.create((double)x, (double)x));
	}
	
	public static void ejemploQ(String fileIn, String fileOut, Fecha c1, Fecha c2){
		Preconditions.checkArgument(c2.compareTo(c1) > 0);
		Stream2.fromFile(fileIn).map(x->x.split(","))
				.<Fecha>map(x-> Fecha.create(new Integer(x[0]),new Integer(x[1]),new Integer(x[2])))
				.filter(x->c1.compareTo(x)< 0 && c2.compareTo(x)>0)
				.sorted()
				.mapToStr(x->x.toString())
				.toFile(fileOut);
	}
	
	public static IntStream ejemploR(String fileIn){
		return Stream2.fromFile(fileIn).map(x->x.split(",")).flatMap(x->Arrays.stream(x)).mapToInt(x-> new Integer(x));
	}
	
	public enum Cuadrante{PRIMER_CUADRANTE, SEGUNDO_CUADRANTE, TERCER_CUADRANTE, CUARTO_CUADRANTE};
	
	public static Cuadrante getCuadrante(Punto2D p){
		return null;
	}
	
	public static Multimap<Cuadrante,Punto2D> ejemploS1(Stream<Punto2D> st){
		return st.map(x->Entry.<Cuadrante,Punto2D>create(getCuadrante(x),x)).collect(Collectors2.toListMultimap());
	}
	
	public static Map<Cuadrante,List<Punto2D>> ejemploT1(Stream<Punto2D> st){
		return st.collect(Collectors.groupingBy(Ejemplos::getCuadrante));
	}
	
	public static Map<Cuadrante,Double> ejemploU(Stream<Punto2D> st){
		return st.collect(Collectors.groupingBy(Ejemplos::getCuadrante, 
							Collectors.<Punto2D,Double>reducing(0.,x->x.getX(),(x,y)->x+y)));
	}
		
	public static Multiset<Cuadrante> ejemploV(Stream<Punto2D> st){
		return st.map(Ejemplos::getCuadrante).collect(Collectors2.toMultiset());	
	}
	
	public static Map<Cuadrante,Long> ejemploW(Stream<Punto2D> st){
		return st.collect(Collectors.groupingBy(Ejemplos::getCuadrante, Collectors.counting()));
	}

	
	public static Long ejemploX(String s){
		return s.chars().filter(x->Character.isLowerCase(x)).count();
	}
	
	public static String ejemploY(List<String> ls){
		return ls.stream().max(Comparator.comparing(Ejemplos::ejemploX)).get();
	}
	
	public static IntSummaryStatistics ejemploZ(List<Integer> ls){
		return ls.stream().collect(Collectors.summarizingInt(x->x));
	}
}
