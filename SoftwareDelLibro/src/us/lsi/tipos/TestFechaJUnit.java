package us.lsi.tipos;

import static org.junit.Assert.*;

import org.junit.Test;


public class TestFechaJUnit {
	
	@Test(expected=IllegalArgumentException.class)
	public void testFechaImplIntegerIntegerInteger() {
		@SuppressWarnings("unused")
		Fecha f = Fecha.create(29, 2, 2009);
	}
	@Test
	public void testGetDiaSemana1() {
	    Fecha f = Fecha.create(8, 10, 2009);
	    assertEquals("Jueves",f.getDiaSemana());
	}
	@Test
	public void testGetDiaSemana2() {
	    Fecha f = Fecha.create(1, 1, 2000);
	    assertEquals("Sábado",f.getDiaSemana());
	}
	@Test
	public void testGetDiaSemana3() {
	    Fecha f = Fecha.create("1 de Marzo de 2009");
	    assertEquals("Domingo",f.getDiaSemana());
	}
	@Test
	public void testToString() {
		Fecha f = Fecha.create(8, 10, 2009);
		assertEquals("Jueves a 8 de Octubre de 2009",f.toString());
	}
	@Test
	public void testRestaInteger() {
		Fecha f = Fecha.create(8, 10, 2009);
		Fecha f2 = Fecha.create(28, 9, 2009);
		assertEquals(f.resta(10),f2);
	}
	@Test
	public void testRestaFecha() {
		Fecha f = Fecha.create(26, 9, 2009);
		Fecha f2 = Fecha.create(1, 1, 2009);
		Integer a = 268;
		assertEquals(f.resta(f2),a);
	}
	@Test
	public void testSuma() {
		Fecha f = Fecha.create(26, 9, 2009);
		Fecha f2 = Fecha.create(3, 10, 2009);
		Integer a = 7;
		assertEquals(f.suma(a),f2);
	}

}
