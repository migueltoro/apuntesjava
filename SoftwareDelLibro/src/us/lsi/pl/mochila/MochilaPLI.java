package us.lsi.pl.mochila;

import us.lsi.pd.mochila.ProblemaMochila;
import us.lsi.pl.AlgoritmoPLI;

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

}
