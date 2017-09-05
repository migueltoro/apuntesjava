package us.lsi.bt;

import java.util.List;

import com.google.common.collect.Lists;

import us.lsi.math.Math2;

public class AlgunosTestsBT<S  extends SolucionBT, A> {

	/**
	 * Dado un estado se ejecuta la lista de acciones sobre el mismo haciendo avances sucesivos 
	 * y posteriormente los retrocedes correspondientes
	 * 
	 * @param <S> Tipo de la solución
	 * @param <A> Tipo de la alternativa
	 * @param e Un Estado inicial
	 * @param ls Una lista de acciones
	 */
	public static <S extends SolucionBT, A> void test1(EstadoBT<S,A> e, List<A> ls) {
		int i=0;
		System.out.println("Avanza");
		for(A a:ls){
			System.out.println(i+"=====");
			System.out.println("Estado = "+e);
			System.out.println("Alternativas = "+e.getAlternativas());
			System.out.println("Contiene la alternativa = "+ e.getAlternativas().contains(a));
			i++;
			e = e.avanza(a);
		}
		System.out.println("Final =====");
		System.out.println("Estado = "+e);
		System.out.println("Es final = "+e.esCasoBase());
		System.out.println("\n\nRetrocede");
		A a;
		for(i=ls.size()-1;i>=0;i--){
			a = ls.get(i);
			System.out.println(i+"=====");
			System.out.println("Estado = "+e);			
			e = e.retrocede(a);
		}
		System.out.println("Inicial =====");
		System.out.println("Estado = "+e);
	}
	/**
	 * Dado un estado se ejecuta una lista de acciones, escogidas aleatoriamente 
	 * entre las posibles, haciendo avences sucesivos 
	 * y posteriormente los retrocedes correspondientes
	 * 
	 * @param <S> Tipo de la solución
	 * @param <A> Tipo de la alternativa
	 * @param e Un Estado inicial
	 */
	public static <S extends SolucionBT, A> void test2(EstadoBT<S,A> e){
		Math2.initRandom();
		System.out.println("Avanza");
		int i=0;
		List<A> alternativas;
		List<A> alternativasEscogidas = Lists.newArrayList();
		A a;
		while(true){
			if(e.esCasoBase() || e.estaFueraDeRango()) break;
			System.out.println(i+"=====");
			System.out.println("Estado = "+e);
			alternativas = e.getAlternativas();
			System.out.println("Alternativas = "+alternativas);
			if (!alternativas.isEmpty()) {
				Integer n = Math2.getEnteroAleatorio(0, alternativas.size());
				a = alternativas.get(n);
				System.out.println("Alternativa Escogida = "+a);
				alternativasEscogidas.add(a);
				e = e.avanza(a);
			}else{
				break;
			}
			i++;
		}
		System.out.println("Final =====");
		System.out.println("Estado = "+e);
		System.out.println("Es final = "+e.esCasoBase());
		System.out.println("Está fuera de rango = "+e.estaFueraDeRango());
		System.out.println("\n\nRetrocede");
		for(i=alternativasEscogidas.size()-1;i>=0;i--){
			a = alternativasEscogidas.get(i);
			System.out.println(i+"=====");
			System.out.println("Estado = "+e);			
			e = e.retrocede(a);
		}
		System.out.println("Inicial =====");
		System.out.println("Estado = "+e);
	}
}
