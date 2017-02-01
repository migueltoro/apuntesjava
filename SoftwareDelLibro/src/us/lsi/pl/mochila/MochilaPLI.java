package us.lsi.pl.mochila;

import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pl.AlgoritmoPLI;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MochilaPLI {

	
	public static String getConstraints(){
		ProblemaMochila.leeObjetosDisponibles(AlgoritmoPLI.getRaiz()+"objetosMochila.txt");
		ProblemaMochila.capacidadInicial = 78;
		int num = ProblemaMochila.getObjetosDisponibles().size();
		String r = "";
		r = r+"max: ";
		for(int i =0;i<num;i++){
			if (i!=0) r = r+"+";
			r = r +AlgoritmoPLI.getFactor(ProblemaMochila.getValorObjeto(i),AlgoritmoPLI.getVariable("x",i));
		}		
		r = r+";\n\n";
		for(int i =0;i<num;i++){
			if (i!=0) r = r+"+";
			r = r +AlgoritmoPLI.getFactor(ProblemaMochila.getPesoObjeto(i),AlgoritmoPLI.getVariable("x",i));
		}
		r = r +"<=";
		r = r +ProblemaMochila.capacidadInicial;
		r = r+";\n\n";
		for(int i =0;i<num;i++){
			r = r +AlgoritmoPLI.getVariable("x",i)+"<="+ProblemaMochila.getNumMaxDeUnidades(i)+";\n";
		}
		r = r +"int ";
		for(int i =0;i<num;i++){
			if (i!=0) r = r+",";
			r = r +AlgoritmoPLI.getVariable("x",i);
		}
		r = r+";\n";		
		return r;
	}
	
	
	public static String getConstraints2(){
		ProblemaMochila.leeObjetosDisponibles(AlgoritmoPLI.getRaiz()+"objetosMochila.txt");
		ProblemaMochila.capacidadInicial = 78;
		int num = ProblemaMochila.getObjetosDisponibles().size();
		String r = "max: ";
		r = r + IntStream.range(0, num)
				.boxed()
				.map(i->ProblemaMochila.getValorObjeto(i)+" x"+i)
				.collect(Collectors.joining("+", "", ";\n\n"));
		
		r = r + IntStream.range(0, num)
				.boxed()
				.map(i->ProblemaMochila.getValorObjeto(i)+" x"+i)
				.collect(Collectors.joining("+", "", " <= "+ ProblemaMochila.capacidadInicial+";\n\n"));
		
		r = r + IntStream.range(0, num)
				.boxed()
				.map(i->" x"+i+"<="+ProblemaMochila.getNumMaxDeUnidades(i)+";\n")
				.collect(Collectors.joining("","","\n"));
		
		r = r +"int ";
		r = r + IntStream.range(0, num)
				.boxed()
				.map(i->" x"+i)
				.collect(Collectors.joining(",","",";\n"));		
		return r;
	}
}
