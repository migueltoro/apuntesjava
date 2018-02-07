package us.lsi.algoritmos;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public  class AbstractAlgoritmo  {

	
	public static PrintStream file = System.out;
	
	/**
	 * @return - Nombre del fichero dónde se almacenará el resultado
	 */
	public static PrintStream getFile() {
		return file;
	}

	/**
	 * @param f - Nombre del fichero dónde se almacenará el resultado
	 */
	public static void setFile(String f) {
		try {
			file = new PrintStream(new File(raiz+f));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("No se puede abrir el fichero "+file);
		}
	}
	
	public static String raiz = "C:\\Users\\Boss\\Documents\\apuntesjava\\SoftwareDelLibro\\ficheros\\";
			

	/**
	 * Si se obtendrán métricas o no
	 */
	public static boolean metricasOK = false;
	
	/**
	 * La métricas calculadas
	 */
	public static Metricas metricas = null;
	
	public static void calculaMetricas(){
		metricasOK = true;
		metricas = new Metricas();
	}
	
}
