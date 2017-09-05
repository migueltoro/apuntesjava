package us.lsi.geometria;

import java.awt.Graphics2D;
import java.util.List;

import com.google.common.collect.Lists;

public class ObjetoGeometricoAgregado2D implements ObjetoGeometrico2D {
	
	public static ObjetoGeometricoAgregado2D create() {
		return new ObjetoGeometricoAgregado2D();
	}

	public static ObjetoGeometricoAgregado2D create(
			List<ObjetoGeometrico2D> objetosGeometricos) {
		return new ObjetoGeometricoAgregado2D(objetosGeometricos);
	}

	public static ObjetoGeometricoAgregado2D create(ObjetoGeometricoAgregado2D a) {
		return new ObjetoGeometricoAgregado2D(a);
	}

	private List<ObjetoGeometrico2D> objetosGeometricos;
		
	private ObjetoGeometricoAgregado2D() {
		this.objetosGeometricos = Lists.newArrayList();
	}
	
	private ObjetoGeometricoAgregado2D(List<ObjetoGeometrico2D> objetosGeometricos) {
		this.objetosGeometricos = objetosGeometricos;
	}
	
	private ObjetoGeometricoAgregado2D(ObjetoGeometricoAgregado2D a) {
		super();
		this.objetosGeometricos = a.getObjetosGeometricos();
	}

	@Override
	public ObjetoGeometrico2D rota(Punto2D p, Double angulo) {
		ObjetoGeometricoAgregado2D od = ObjetoGeometricoAgregado2D.create();
		
		for(ObjetoGeometrico2D a:this.objetosGeometricos){
			od.add(a.rota(p, angulo));
		}
			
		return od;
	}

	@Override
	public ObjetoGeometrico2D traslada(Vector2D v) {
				ObjetoGeometricoAgregado2D od = ObjetoGeometricoAgregado2D.create();
				
				for(ObjetoGeometrico2D a:this.objetosGeometricos){
					od.add(a.traslada(v));
				}
					
				return od;
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		for(ObjetoGeometrico2D a:this.objetosGeometricos){
			a.draw(g);
		}
	}
	
	
	public void add(ObjetoGeometrico2D a){
		this.objetosGeometricos.add(a);
	}

	public List<ObjetoGeometrico2D> getObjetosGeometricos() {
		return objetosGeometricos;
	}
	
	

}
