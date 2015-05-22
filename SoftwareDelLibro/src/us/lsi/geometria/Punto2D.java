package us.lsi.geometria;

import java.awt.Graphics2D;

import us.lsi.gui.MarcoDeTrabajo;



public class Punto2D implements Comparable<Punto2D>, ObjetoGeometrico2D {	

	private static Punto2D cero = Punto2D.create();
	
	public static Punto2D getOrigen(){
		return cero;
	}
	
	public static Punto2D create(Double x, Double y) {
		return new Punto2D(x, y);
	}

	public static Punto2D create(Punto2D p) {
		return new Punto2D(p.getX(), p.getY());
	}
	
	public static Punto2D create() {
		return new Punto2D();
	}
	
	public static Punto2D create(Vector2D v){
		return create(v.getX(),v.getY());
	}

	private Double x;
	private Double y;

	protected Punto2D(){
		x = 0.;
		y = 0.;
	}
	
	protected Punto2D(Double x, Double y){
		this.x = x;
		this.y = y;
	}
	
	public Double getX(){
		return x;
	}
	public Double getY(){
		return y;
	}

	public void setX(Double x){
		this.x = x;
	}
    public void setY(Double y){
    	this.y = y;
    }
    
    public Punto2D add(Vector2D v){
    	return create(this.x+v.getX(),this.y+v.getY());
    }
    
    public Vector2D minus(Punto2D v){
    	return Vector2D.createCartesiano(this.x-v.getX(),this.y-v.getY());
    }
    
    public Double getDistanciaA(Punto2D p) {
		return minus(p).getModulo();
	}
    
	public Double getDistanciaAlOrigen() {
		return Vector2D.create(this).getModulo();
	} 
	
	public Punto2D traslada(Vector2D v){
		return add(v);
	}
    
	public Punto2D rota(Punto2D p, Double angulo){
		Vector2D v = minus(p).rota(angulo);
		return p.add(v);
	}
	
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		MarcoDeTrabajo.fill(g, this);
	}
	
	public boolean dominaA(Punto2D p){
		return !this.equals(p) && this.getX() >= p.getX() && this.getY() >= p.getY();
	}
	
	public String toString() {
    	String s="("+getX()+","+ getY()+")";
    	return s;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Punto2D))
			return false;
		Punto2D other = (Punto2D) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public int compareTo(Punto2D p) {
		if(p==null || this.getX() ==null || this.getY() == null|| p.getX() ==null || p.getY() == null ){
	           throw new NullPointerException();
	    }
		int r = getX().compareTo(p.getX());
		if(r==0){
			r = getX().compareTo(p.getX());
		}
		return r;
	}

		
	
}
