package us.lsi.geometria;

import java.awt.Graphics2D;

public interface ObjetoGeometrico2D {
	ObjetoGeometrico2D rota(Punto2D p, Double angulo);
	ObjetoGeometrico2D traslada(Vector2D v);
	void draw(Graphics2D g);
}
