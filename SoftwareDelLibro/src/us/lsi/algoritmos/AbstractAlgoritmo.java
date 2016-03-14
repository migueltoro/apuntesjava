package us.lsi.algoritmos;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public  class AbstractAlgoritmo  {

	private static PrintStream file = System.out;
	
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
			file = new PrintStream(new File(f));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("No se puede abrir el fichero "+file);
		}
	}
	
	private static String raiz = "C:\\Users\\Boss\\Documents\\apuntesjava\\SoftwareDelLibro\\ficheros\\";
			
	public static String getRaiz(){
		return raiz;
		
	}

	public static void setRaiz(String r){
		raiz=r;		
	}
}
