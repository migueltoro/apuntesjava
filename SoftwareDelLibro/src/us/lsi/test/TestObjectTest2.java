package us.lsi.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import us.lsi.math.Math2;
import us.lsi.tipos.Fecha;

public class TestObjectTest2 {
	Fecha f1;
	Fecha f2;
	Fecha f3;
	Fecha f4;
	@Before
	public void setUp() throws Exception {
		f1 = Fecha.create(1, 1, 2000);
		f2 = Fecha.create("1 de Enero de 2000");
		f3 = Fecha.create(1, 1, 2000);
		f4 = Fecha.create(20, 2, 2009);
	}

	@Test
	public void testReflexivaIgualdad() {
		assertTrue(f1.equals(f1));
	}

	@Test
	public void testSimetricaIgualdad() {
		assertTrue(f1.equals(f2)==f2.equals(f1));
	}

	@Test
	public void testTransitivaIgualdad() {
		assertTrue(!(f1.equals(f2)&& f2.equals(f3)) ? true : f1.equals(f3));
	}

	@Test
	public void testIgualdadHashCode() {
		assertTrue(!(f1.equals(f2)) ? true : f1.hashCode()==f2.hashCode());
	}

	@Test
	public void testIgualdadToString() {
		assertTrue(!(f1.equals(f2)) ? true : f1.toString().equals(f2.toString()));
	}

	@Test
	public void testCopiable() {
		
		Fecha f = Fecha.create(f1);
		assertTrue(f.equals(f1) && f!=f1);
	}

	@Test
	public void testComparableIgualdad() {
		assertTrue((f1.equals(f2) == (f1.compareTo(f2) == 0)));
		assertTrue((f1.equals(f4) == (f1.compareTo(f4) == 0)));
	}

	@Test
	public void testComparableAntisimetrica() {		
		assertTrue(Math2.sgn(f1.compareTo(f4)) == -Math2.sgn(f4.compareTo(f1)));
	}

	@Test
	public void testComparableTransitiva() {
		assertTrue(!(f1.compareTo(f2)<=0 && f2.compareTo(f3)<=0 ) ? true : f1.compareTo(f3)<=0); 
	}
	@Test
	public void testComparableTransitiva2() {
		assertTrue(!(f1.compareTo(f2)<=0 && f2.compareTo(f4)<=0 ) ? true : f1.compareTo(f4)<=0); 
	}
}
