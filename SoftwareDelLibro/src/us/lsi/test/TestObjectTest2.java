package us.lsi.test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import us.lsi.math.Math2;


public class TestObjectTest2 {
	LocalDate f1;
	LocalDate f2;
	LocalDate f3;
	LocalDate f4;
	@Before
	public void setUp() throws Exception {
		f1 = LocalDate.of(2000, 1, 1);
		f3 = LocalDate.of(2000,1,1);
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
		
		LocalDate f = LocalDate.of(f1.getYear(),f1.getMonthValue(),f1.getDayOfMonth());
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
