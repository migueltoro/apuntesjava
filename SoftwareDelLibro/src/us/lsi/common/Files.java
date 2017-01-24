package us.lsi.common;

import java.io.*;





public class Files {
		
	
	
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
}

