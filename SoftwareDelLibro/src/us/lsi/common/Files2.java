package us.lsi.common;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;





public class Files2 {
		
	
	
	private static PipedOutputStream os = null;
	private static PipedInputStream is = null;
	private final static Integer bufferSize=1000000;
	private static ObjectOutputStream p;
	private static ObjectInputStream p2;
	
	public static Boolean existeFichero(String f){
		File file = new File(f);
		return file.exists();
	}
	
	public static <T extends Serializable> void guarda(T o, String f){
		if(existeFichero(f))
			throw new IllegalArgumentException("El fichero " + f + " ya existe");
		try{
			FileOutputStream ostream = new FileOutputStream(f);
			p = new ObjectOutputStream(ostream);
			p.writeObject(o);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T carga(String f){
		T o1=null;
		try{
		   FileInputStream istream = new FileInputStream(f);
		   p2 = new ObjectInputStream(istream);
		   o1 =(T) p2.readObject();
		}
		catch(Exception e){e.printStackTrace();}
		return o1;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T copia(T o){
		T o1 = null;
		try{
			if(os ==null)
				os = new PipedOutputStream();
			
			if(is==null)
				is = new PipedInputStream(os,bufferSize);
			
			ObjectOutputStream p = new ObjectOutputStream(os);
			p.writeObject(o);
			
			ObjectInputStream p1 = new ObjectInputStream(is);
			o1=(T)p1.readObject();
		}
		catch(Exception e){e.printStackTrace();}
		return o1;		
	}

	/**
	 * @param f Un nombre de fichero
	 * @return Un objeto adecuado para escribir el fichero abierto con el nombre f
	 */
	public static PrintWriter getWriter(String f) {
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(f)));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		return fout;
	}

	/**
	 * @param f El nombre del fichero
	 * @return Una lista con las líneas del fichero
	 */
	public static List<String> getLines(String f) {
		List<String> lineas = null;
		try {
			lineas = new BufferedReader(new FileReader(f)).lines().collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return lineas;
	}
	
	/**
	 * @param f El nombre del fichero
	 * @return El BufferedReader asociado
	 */
	public static BufferedReader get(String f) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return br;
	}
}

