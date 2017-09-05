package us.lsi.dyv.puntos;

import java.util.List;
import java.util.stream.Collectors;
import us.lsi.geometria.ParDePuntos;
import us.lsi.geometria.Punto2D;
import us.lsi.stream.Stream2;

import com.google.common.base.*;



public class TestPuntosMasCercanos {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Punto2D> lista = Stream2
				.fromFile("puntos.txt")
				.<Punto2D> map(
						s -> {
							String[] ps = s.split(",");
							Preconditions.checkArgument(ps.length == 2);
							return Punto2D.create(new Double(ps[0]),new Double(ps[1]));
						}).collect(Collectors.<Punto2D> toList());
		ParDePuntos r1 = ListasDePuntos.parMasCercano(lista);
		ParDePuntos r2 = ListasDePuntos.parMasCercanoBase(0, lista.size(), lista);
		System.out.println(r1+","+r2);
	}

}
