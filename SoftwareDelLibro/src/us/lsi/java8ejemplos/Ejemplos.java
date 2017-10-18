package us.lsi.java8ejemplos;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;








import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import us.lsi.geometria.Punto2D;
import us.lsi.math.Math2;
import us.lsi.stream.Stream2;
import us.lsi.stream.Collectors2;

import java.util.AbstractMap.SimpleEntry;

/**
*
*
* @author Miguel Toro
*/

public class Ejemplos {

	/**
	 * @param <T> El tipo de los elementos
	 * @param st Un Stream
	 * @return Crear una lista a partir de un Stream de valores
	 */
	public static <T> List<T> ejemploA(Stream<T> st){
		return st.collect(Collectors.<T>toList());
	}
	
	/**
	 * @param ls una lista
	 * @return Dada una lista de objetos de tipo Punto, devolver otra lista con la coordenada X de esos puntos
	 */
	public static List<Double> ejemploB(List<Punto2D> ls){
		return ls.stream().map(x->x.getX()).collect(Collectors.toList());
	}
	
	/**
	 * @param ls Una lista
	 * @return Si son impares todos los elementos de la lista.
	 */
	public static boolean ejemploC(List<Integer> ls){
		return ls.stream().allMatch(x->x%2==1);
	}
	
	/**
	 * @param ls Una lista
	 * @return Si existe alguno impar y primo
	 */
	public static boolean ejemploD(List<Integer> ls){
		return ls.stream().anyMatch(x->x%2==1 && Math2.esPrimo(x));
	}
	
	/**
	 * @param ls Una lista
	 * @return La suma de todos los elementos de la lista.
	 */
	public static Double ejemploE(List<Double> ls){
		return ls.stream().mapToDouble(x->x).sum();
	}
	
	/**
	 * @param ls Una lista
	 * @return La suma de los cuadrados de todos los elementos de la lista
	 */
	public static Integer ejemploF(List<Integer> ls){
		return ls.stream().mapToInt(x->x*x).sum();
	}
	
	/**
	 * @param ls Una lista
	 * @param umbral Un elemento de referencia
	 * @return El primer real que encuentre que sea mayor que un umbral dado como parámetro
	 */
	public static Double ejemploG(List<Double> ls, Double umbral){
		return ls.stream().filter(x-> x > umbral).findFirst().get();
	}
	
	/**
	 * @param ls Una lista
	 * @return Una lista de Punto con los puntos simétricos sobre el eje Y
	 */
	public static List<Punto2D> ejemploH(List<Punto2D> ls){
		return ls.stream().map(x->Punto2D.create(-x.getX(),x.getY())).collect(Collectors.toList());
	}
	
	/**
	 * @param ls Una lista
	 * @return El punto con menor coordenada X.
	 */
	public static Punto2D ejemploI(List<Punto2D> ls){
		return ls.stream().min(Comparator.comparing(Punto2D::getX)).get();
	}
	
	/**
	 * @param ls Una lista
	 * @return Número de puntos de la lista en el primer cuadrante
	 */
	public static Long ejemploJ(List<Punto2D> ls){
		return ls.stream().filter(x->x.getX()>=0. && x.getY()>=0.).count();
		
	}
	
	/**
	 * @param ls Una array
	 * @return Una lista con todos los elementos del array
	 */
	public static List<Punto2D> ejemploK(Punto2D[] ls){
		return Arrays.stream(ls).collect(Collectors.toList());
	}
	
	/**
	 * @pos Guarda en un fichero de texto los números primos hasta un número n dado.
	 * @param fileOut  Un fichero 
	 * @param limit Un número de referencia
	 */
	public static void ejemploL(String fileOut, Integer limit){
		Stream2.iterate(1, x->Math2.siguientePrimo(x)).whilePredicate(x-> x<=limit).mapToStr(x->x.toString()).toFile(fileOut);
	}
	
	/**
	 * @param st Un Stream de listas
	 * @return Stream de con los elementos de todas las listas
	 */
	public static Stream<Integer> ejemploM(Stream<List<Integer>> st){
		return st.flatMap(x->x.stream());
	}
	
	/**
	 * @pos Guarda en un fichero de texto el cuadrado de los números primos hasta un número n dado.
	 * @param fileOut Un fichero
	 * @param limit Un número de referencia
	 */
	public static void ejemploN(String fileOut, Integer limit){
		Stream2.iterate(1, x->Math2.siguientePrimo(x)).whilePredicate(x-> x<=limit).map(x->x*x).mapToStr(x->x.toString()).toFile(fileOut);
	}
	
	/**
	 * @param fileIn Un fichero de texto que contiene en cada línea un número entero
	 * @return Un IntStream
	 */
	public static IntStream ejemploO(String fileIn){
		return Stream2.fromFile(fileIn).mapToInt(x-> new Integer(x));
	}
	
	/**
	 * @param limit Una referencia
	 * @return Un Stream de Punto con los puntos del plano cuya coordenada sea (X,X), 
	 * siendo X un número primo menor que un número dado
	 */
	public static Stream<Punto2D> ejemploP(Integer limit){
		return Stream2.iterate(1L, x->Math2.siguientePrimo(x)).whilePredicate(x-> x<=limit).<Punto2D>map(x->Punto2D.create((double)x, (double)x));
	}
	
	/**
	 * @pos Genera fileOut a partir de fileIn
	 * @param fileIn Un fichero de texto con una fecha escrita en cada línea
	 * @param fileOut Un fichero con las fechas ordenadas y que estén entre dos fechas dadas
	 * @param c1 Límite inferior de las fechas
	 * @param c2 Límite superior de las fecha
	 */
	public static void ejemploQ(String fileIn, String fileOut, LocalDate c1, LocalDate c2){
		Preconditions.checkArgument(c2.compareTo(c1) > 0);
		Stream2.fromFile(fileIn)
				.map(x-> LocalDate.parse(x))
				.filter(x->c1.compareTo(x)< 0 && c2.compareTo(x)>0)
				.sorted()
				.mapToStr(x->x.toString())
				.toFile(fileOut);
	}
	
	/**
	 * @param fileIn Un fichero de texto que contiene en cada línea una lista de números enteros separados por comas
	 * @return Un IntStream
	 */
	public static IntStream ejemploR(String fileIn){
		return Stream2.fromFile(fileIn).map(x->x.split(",")).flatMap(x->Arrays.stream(x)).mapToInt(x-> new Integer(x));
	}
	
	public enum Cuadrante{PRIMER_CUADRANTE, SEGUNDO_CUADRANTE, TERCER_CUADRANTE, CUARTO_CUADRANTE};
	
	public static Cuadrante getCuadrante(Punto2D p){
		Cuadrante c;
		if(p.getX() >=0 && p.getY() >=0){
			c = Cuadrante.PRIMER_CUADRANTE;
		} else if(p.getX() <=0 && p.getY() >=0){
			c = Cuadrante.SEGUNDO_CUADRANTE;
		} else if(p.getX() <=0 && p.getY() <=0){
			c = Cuadrante.TERCER_CUADRANTE;
		} else {
			c = Cuadrante.CUARTO_CUADRANTE;
		}
		return c;
	}
	
	/**
	 * @pos Agrupa los puntos por cuadrantes
	 * @param st Un Stream
	 * @return Multimap&lt;Cuadrante,Punto&gt; en el que se asocia a cada cuadrante, los puntos del Stream que están en ese cuadrante
	 */
	public static Multimap<Cuadrante,Punto2D> ejemploS1(Stream<Punto2D> st){
		return st.map(x->new SimpleEntry<Cuadrante,Punto2D>(getCuadrante(x),x)).collect(Collectors2.toListMultimap());
	}
	
	/**
	 * @pos Agrupa los puntos por cuadrantes
	 * @param st Un Stream
	 * @return Map&lt;Cuadrante,List&lt;Punto&gt;&gt; en el que se asocia a cada cuadrante, los puntos del Stream que están en ese cuadrante
	 */
	public static Map<Cuadrante,List<Punto2D>> ejemploT1(Stream<Punto2D> st){
		return st.collect(Collectors.groupingBy(Ejemplos::getCuadrante));
	}
	
	/**
	 * @pos Suma las coordenadas X de los puntos en cada cuadrante
	 * @param st Un Stream
	 * @return Map@lt;Cuadrante,Double&gt; en el que se asocia a cada cuadrante, la suma de las coordenadas X de los puntos de ese cuadrante
	 */
	public static Map<Cuadrante,Double> ejemploU(Stream<Punto2D> st){
		return st.collect(Collectors.groupingBy(Ejemplos::getCuadrante, 
							Collectors.<Punto2D,Double>reducing(0.,x->x.getX(),(x,y)->x+y)));
	}
		
	/**
	 * @pos Cuenta cuántos puntos hay de cada cuadrante en el Stream
	 * @param st Un Stream
	 * @return  Un Multiset&lt;Cuadrante&gt;
	 */
	public static Multiset<Cuadrante> ejemploV(Stream<Punto2D> st){
		return st.map(Ejemplos::getCuadrante).collect(Collectors2.toMultiset());	
	}
	
	/**
	 * @pos Cuenta cuántos puntos hay de cada cuadrante en el Stream
	 * @param st Un Stream 
	 * @return Un Map&lt;Cuadrante,Long&gt;
	 */
	public static Map<Cuadrante,Long> ejemploW(Stream<Punto2D> st){
		return st.collect(Collectors.groupingBy(Ejemplos::getCuadrante, Collectors.counting()));
	}

	
	/**
	 * @param s Un String
	 * @return Número de caracteres en minúscula que tiene la cadena
	 */
	public static Long ejemploX(String s){
		return s.chars().filter(x->Character.isLowerCase(x)).count();
	}
	
	/**
	 * @param ls Una lista
	 * @return La cadena que tiene un mayor número de caracteres en minúscula
	 */
	public static String ejemploY(List<String> ls){
		return ls.stream().max(Comparator.comparing(Ejemplos::ejemploX)).get();
	}
	
	/**
	 * @param ls Una lista
	 * @return El máximo, el mínimo, la media y la suma.
	 */
	public static IntSummaryStatistics ejemploZ(List<Integer> ls){
		return ls.stream().collect(Collectors.summarizingInt(x->x));
	}
	
	/**
	 * @param n Un entero
	 * @return Si es primo
	 */
	public static boolean esPrimo1(Long n){
		Long sqrt = (long)Math.sqrt((double)n);
		return !LongStream.rangeClosed(2, sqrt).anyMatch(x->Math2.esDivisible(n, x));
	}
	
	/**
	 * @param n Un entero
	 * @return Si es primo
	 */
	public static boolean esPrimo2(Long n){
		Long sqrt = (long)Math.sqrt((double)n);
		Long e = 2L;
		Boolean a = false;
		while(e <= sqrt){	   
	      	a = Math2.esDivisible(n, e);
	      	if(a) break; 
	   		e = e + 1;		   
        }
		return !a;
	}
	/**
	 * @param n Un entero
	 * @return Primer primo mayor que a
	 */
	public static Long siguientePrimo1(Long n){
		Long e0 = n%2==0?n+1:n+2;
		return Stream.iterate(e0, e->e+2).filter(e->Math2.esPrimo(e)).findFirst().get();
	}
	/**
	 * @param n Un entero
	 * @return Primer primo mayor que a
	 */
	public static Long siguientePrimo2(Long n){
		Long e = n%2==0?n+1:n+2;
		Long a = null;
		while(true){	   
	      	if(esPrimo2(e))  {
	      		a = e;
	      		break;
	      	}
	   		e = e +2;		   
        }
		return a;
	}
	/**
	 * @param limit Un entero
	 * @return Los  primos menores o iguales a limit
	 */
	public static List<Long> primosMenoresOIgualesA1(Long limit){
		return Stream2.iterate(1L, x->siguientePrimo1(x)).whilePredicate(x->x<=limit).collect(Collectors.toList());
	}
	/**
	 * @param limit Un entero
	 * @return Los  primos menores o iguales a limit
	 */
	public static List<Long> primosMenoresOIgualesA2(Long limit){
		Long e = 1L;
		List<Long> a = Lists.newArrayList();
		while(e<=limit){	   
	      	a.add(e);
	   		e = siguientePrimo2(e);		   
        }
		return a;
	}
	/**
	 * @param limit Un entero
	 * @return Suma de los  primos menores o iguales a limit
	 */
	public static Long sumaPrimosMenoresOIgualesA1(Long limit){
		return Stream2.iterate(1L, x->siguientePrimo1(x)).whilePredicate(x->x<=limit).reduce(1L,(x,y)->x+y);
	}
	/**
	 * @param limit Un entero
	 * @return Suma de los  primos menores o iguales a limit
	 */
	public static Long sumaPrimosMenoresOIgualesA2(Long limit){
		Long e = 1L;
		Long a = 0L;
		while(e<=limit){	   
	      	a = a+e;
	   		e = siguientePrimo2(e);		   
        }
		return a;
	}
		
}
