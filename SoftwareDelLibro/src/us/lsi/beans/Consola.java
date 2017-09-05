package us.lsi.beans;


//import org.jdesktop.beansbinding.PropertyStateEvent;
//import org.jdesktop.beansbinding.PropertyStateListener;

public class Consola {
	
	private static int nc = 1;
	private int id;
	private int x;
	private int y;
	private String titulo;
	
	Consola() {
		super();
		id = nc;
		nc++;
	}

	public int getId() {
		return id;
	}

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
		System.out.println(this);
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "Consola [id=" + id + ", x=" + x + ", y=" + y + ", titulo="
				+ titulo + "]";
	}
	
}
