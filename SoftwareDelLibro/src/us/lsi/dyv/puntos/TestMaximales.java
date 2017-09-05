package us.lsi.dyv.puntos;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.geometria.Punto2D;
import us.lsi.stream.Stream2;


import com.google.common.base.Preconditions;


public class TestMaximales {

	
	public static void main(String[] args) {
		List<Punto2D> lista = Stream2
				.fromFile("puntos.txt")
				.<Punto2D> map(
						s -> {
							String[] ps = s.split(",");
							Preconditions.checkArgument(ps.length == 2);
							return Punto2D.create(new Double(ps[0]),new Double(ps[1]));
						}).collect(Collectors.<Punto2D> toList());

		Set<Punto2D> r1 = ListasDePuntos.puntosMaximales(lista);
		Set<Punto2D> r2 = ListasDePuntos.puntosMaximalesBase(0, lista.size(),
				lista);
		System.out.println(r1 + "," + r2 + " ----  " + r2.equals(r2));
	}

}
