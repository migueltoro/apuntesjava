package us.lsi.dyv.problemasdelistas;

import static org.junit.Assert.*;

import java.util.List;





import org.junit.Test;

import us.lsi.common.*;
import us.lsi.math.Math2;

import com.google.common.collect.Ordering;

public class TestProblemasListas {

	@Test
	public void testReordenaMedianteBanderaHolandesa() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Ordering<Double> ord = Ordering.natural();
		Double pivote = lista.get(0);
		Tuple2<Integer,Integer> p = ProblemasDeListas.reordenaMedianteBanderaHolandesa(lista, pivote, 0,lista.size(), ord);
		for(int i=0;i<p.getV1();i++){
			assertTrue(lista.get(i)<pivote);
		}
		for(int i=p.getV1();i<p.getV2();i++){
			assertTrue(lista.get(i).equals(pivote));
		}
		for(int i=p.getV2();i<lista.size();i++){
			assertTrue(lista.get(i)>pivote);
		}
	}

	@Test
	public void testReordenaSobrePivote() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Ordering<Double> ord = Ordering.natural();
		Double pivote = lista.get(0);
		Integer p = ProblemasDeListas.reordenaSobrePivote(lista, pivote, 0,lista.size(), ord);
		for(int i=0;i<p;i++){
			assertTrue(lista.get(i)<pivote);
		}
		for(int i=p;i<lista.size();i++){
			assertTrue(lista.get(i)>=pivote);
		}
	}

	@Test
	public void testOrdenaBase() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Ordering<Double> ord = Ordering.natural();
		ProblemasDeListas.ordenaBase(lista,0,lista.size(),ord);
		assertTrue(ord.isOrdered(lista));
	}
	
	@Test
	public void testGetEnteroAleatorio() {
		Integer a = Math2.getEnteroAleatorio(100, 1000);
		Integer b = Math2.getEnteroAleatorio(100, 1000);
		Integer c = Math2.getEnteroAleatorio(a, a+b);
		assertTrue(c>=a);
		assertTrue(c<a+b);
	};

}
