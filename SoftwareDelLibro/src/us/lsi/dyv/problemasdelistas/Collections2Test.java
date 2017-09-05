package us.lsi.dyv.problemasdelistas;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import org.junit.Test;

import us.lsi.common.Comparator2;
import us.lsi.dyv.problemasdelistas.ProblemasDeListas.SubSecuencia;
import us.lsi.math.Math2;

import com.google.common.collect.Lists;



public class Collections2Test {

	@Test
	public void testBinarySearchList() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Double key = Math2.getDoubleAleatorio(-20.,20.);
		ProblemasDeListas.sort(lista);
		int r1 = ProblemasDeListas.binarySearch(lista, key);
		int r2 = Collections.binarySearch(lista, key);
		System.out.println(r1+","+r2);
		assertTrue(r1==r2);
	}
	
	@Test
	public void testSortList() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Comparator2<Double> ord = Comparator2.<Double>from(Comparator.naturalOrder());
		ProblemasDeListas.sort(lista);
		assertTrue(ord.isOrdered(lista));
	}

	@Test
	public void testSortListComparator() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Comparator2<Double> ord = Comparator2.<Double>from(Comparator.reverseOrder());
		ProblemasDeListas.sort(lista,ord);
		assertTrue(ord.isOrdered(lista));
	}
	
	@Test
	public void testEscogeKesimo() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Integer k = Math2.getEnteroAleatorio(0,lista.size());
		Double r = ProblemasDeListas.getKesimo(lista,k);
		ProblemasDeListas.sort(lista);
		assertTrue(lista.get(k).equals(r));
	}

	@Test
	public void testEscogeKesimoComparator() {
		List<Double> lista = Math2.getListDoubleAleatoria(50, -20., 20.);
		Comparator2<Double> ord = Comparator2.<Double>from(Comparator.reverseOrder());
		Integer k = Math2.getEnteroAleatorio(0,lista.size());
		Double r = ProblemasDeListas.getKesimo(lista,k,ord);
		ProblemasDeListas.sort(lista,ord);
		assertTrue(lista.get(k).equals(r));
	}
	
	@Test
	public void testSubSecuencia() {
		Double[] r = {1., -2., 11., -4., 13., -5., 2., 3.};
		List<Double> lista = Lists.newArrayList();
		for(Double e:r){
			lista.add(e);
		}
		SubSecuencia s = ProblemasDeListas.getSubSecuenciaMaxima(lista);
		assertTrue(new SubSecuencia(lista,2,5).equals(s));
	}
}
