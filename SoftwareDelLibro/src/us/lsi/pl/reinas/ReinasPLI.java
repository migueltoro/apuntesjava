package us.lsi.pl.reinas;

import us.lsi.pl.AlgoritmoPLI;

public class ReinasPLI {

	public static Integer numeroDeReinas = 8;
	
	public static String getConstraints(){
		String r = "min: ;\n\n";
		Integer n = numeroDeReinas;
		boolean first = true;
		
		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first) first = false; else r = r + "+";
				r = r + AlgoritmoPLI.getVariable("x", i, j);
			}
			r = r +"=";
			r = r +1;
			r = r+";\n";
		}
		
		r = r+"\n\n";
		
		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first) first = false; else r = r + "+";
				r = r + AlgoritmoPLI.getVariable("x", j, i);
			}
			r = r +"=";
			r = r +1;
			r = r+";\n";
		}
		
		r = r+"\n\n";
		int m;
		for (int d = -n+1; d < n; d++) {
			first = true;
			m=0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {					
					if (d==j-i) {
						if (first) first = false; else r = r + "+";
						r = r + AlgoritmoPLI.getVariable("x", i, j);	
						m++;
					}
				}				
			}
			if (m>0) {
				r = r + "<=";
				r = r + 1;
				r = r + ";\n";
			}
		}
		
		r = r+"\n\n";
		
		for (int d = 0; d < 2*n-2; d++) {
			first = true;
			m=0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {					
					if (d==j+i) {
						if (first) first = false; else r = r + "+";
						r = r + AlgoritmoPLI.getVariable("x", i, j);
						m++;
					}
				}				
			}
			if (m>0) {
				r = r + "<=";
				r = r + 1;
				r = r + ";\n";
			}
		}
		
		r = r+"\n\n";
		
		r = r + "bin ";
		
		first = true;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first) first = false; else r = r + ",";
				r = r + AlgoritmoPLI.getVariable("x", i, j);
			}
			
		}
		
		r = r+";\n\n";
		
		return r;
	}
}
