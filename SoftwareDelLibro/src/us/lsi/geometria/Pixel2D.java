package us.lsi.geometria;

import java.awt.Color;

public class Pixel2D extends Punto2D {

	private Color color;
	
	public Pixel2D() {
		// TODO Auto-generated constructor stub
		super();
		this.color = Color.RED;
	}

	public Pixel2D(Double x, Double y, Color color) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Pixel2D [color=" + color + ","+ super.toString()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Pixel2D))
			return false;
		Pixel2D other = (Pixel2D) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
	
}
