package us.lsi.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestRacionalJUnit {
    Racional r;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testRacionalImpl() {
		r = Racional.create(4, 6);
		int n = r.getNumerador();
		int d = r.getDenominador();
		assertEquals(n,2);
		assertEquals(d,3);
	}

	@Test
	public void testRacionalImplIntegerInteger() {
		r = Racional.create(4, 6);
		int n = r.getNumerador();
		int d = r.getDenominador();
		assertEquals(n,2);
		assertEquals(d,3);
		r = Racional.create(-2, -3);
		n = r.getNumerador();
		d = r.getDenominador();
		assertEquals(n,2);
		assertEquals(d,3);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRacionalImplIntegerInteger1() {
		r = Racional.create(1, 0);
	}
	
	
	@Test
	public void testRacionalImplInteger() {
		r = Racional.create(3);
		int n = r.getNumerador();
		int d = r.getDenominador();
		assertEquals(n,3);
		assertEquals(d,1);
	}

	@Test
	public void testRacionalImplString() {
		r = Racional.create(" -8/ 4");
		int n = r.getNumerador();
		int d = r.getDenominador();
		assertEquals(n,-2);
		assertEquals(d,1);
	}

	@Test(expected=NumberFormatException.class)
	public void testRacionalImplString1() {
		r = Racional.create("2 3");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRacionalImplString2() {
		r = Racional.create("2/3/4");
	}
	
	@Test
	public void testGetNumerador() {
		r = Racional.create(-5,9);
		int n = r.getNumerador();
		assertEquals(n,-5);
	}


	@Test
	public void testGetDenominador() {
		r = Racional.create(6, 3);
		int d = r.getDenominador();
		assertEquals(d,1);
	}
	
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testCompareTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject1() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString1() {
		fail("Not yet implemented");
	}

}
