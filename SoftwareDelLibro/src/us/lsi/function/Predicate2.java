package us.lsi.function;

import java.util.Comparator;

public class Predicate2 {
	
	public static <T extends Object & Comparable<? super T>> boolean esMayor(T e1, T e2){
		return e1.compareTo(e2) > 0;
	}
	public static <T extends Object & Comparable<? super T>> boolean esMayorOIgual(T e1, T e2){
		return e1.compareTo(e2) >= 0;
	}
	public static <T extends Object & Comparable<? super T>> boolean esMenor(T e1, T e2){
		return e1.compareTo(e2) < 0;
	}
	public static <T extends Object & Comparable<? super T>> boolean esMenorOigual(T e1, T e2){
		return e1.compareTo(e2) <= 0;
	}
	public static <T> boolean esMayor(Comparator<? super T> cmp, T e1, T e2){
		return cmp.compare(e1,e2) > 0;
	}
	public static <T> boolean esMayorOIgual(Comparator<? super T> cmp, T e1, T e2){
		return cmp.compare(e1,e2) >= 0;
	}
	public static <T> boolean esMenor(Comparator<? super T> cmp, T e1, T e2){
		return cmp.compare(e1,e2) < 0;
	}
	public static <T> boolean esMenorOigual(Comparator<? super T> cmp, T e1, T e2){
		return cmp.compare(e1,e2) <= 0;
	}
	public static <T> boolean esIgual(Comparator<? super T> cmp, T e1, T e2){
		return cmp.compare(e1,e2) == 0;
	}
	public static <T> boolean estaEnAbierto(Comparator<? super T> cmp, T e, T e1, T e2){
		return cmp.compare(e,e1) > 0 && cmp.compare(e,e2) < 0;
	}
	public static <T extends Object & Comparable<? super T>> boolean estaEnAbierto(T e, T e1, T e2){
		return e.compareTo(e1) > 0 && e.compareTo(e2) < 0;
	}
	public static <T> boolean estaEnCerrado(Comparator<? super T> cmp, T e, T e1, T e2){
		return cmp.compare(e,e1) >= 0 && cmp.compare(e,e2) <= 0;
	}
	public static <T extends Object & Comparable<? super T>> boolean estaEnCerrado(T e, T e1, T e2){
		return e.compareTo(e1) >= 0 && e.compareTo(e2) <= 0;
	}
}
