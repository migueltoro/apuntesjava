package us.lsi.pd.festival;

import us.lsi.algoritmos.Algoritmos;
import us.lsi.pd.AlgoritmoPD;

public class Test {

	public static void main(String[] args) {
		AlgoritmoPD.isRandomize = false;
		ProblemaFestival.create("./ficheros/grupos.txt");
		ProblemaFestival.presupuestoTotal = 10000;
		System.out.println("------");
		for(String dia: ProblemaFestival.gruposPorDiaYHora.keySet()){
			for(String hora: ProblemaFestival.gruposPorDiaYHora.get(dia).keySet()){
				System.out.println("Grupos " + dia + " " + hora + ": "+ ProblemaFestival.gruposPorDiaYHora.get(dia).get(hora));
			}
		}
		System.out.println("------");
		System.out.println("Presupuesto total: " + ProblemaFestival.presupuestoTotal);
		System.out.println("------");
		AlgoritmoPD.calculaMetricas();
		ProblemaFestivalPD p = ProblemaFestivalPD.create();
		AlgoritmoPD.conFiltro = true;
		AlgoritmoPD<Festival, Grupo> a = Algoritmos.createPD(p);
		a.ejecuta();
		if (a.solucionesParciales.get(p) != null){
			System.out.println("Votos: " + a.solucionesParciales.get(p).propiedad);
			System.out.println("Solucion: " + a.getSolucion(p));
			a.showAllGraph("./ficheros/solucionFestival.gv", "SolucionFestival", p);
		}else{
			System.out.println("Solución no encontrada");
		}
		System.out.println(AlgoritmoPD.metricas);
	}
}

