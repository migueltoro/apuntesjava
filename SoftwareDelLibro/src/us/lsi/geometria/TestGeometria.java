package us.lsi.geometria;

import us.lsi.gui.MarcoDeTrabajo;

public class TestGeometria {

	
	public static void main(String[] args) {
		Recta2D r = Recta2D.create(Punto2D.create(), Punto2D.create(1., 1.));
		Recta2D rc = Recta2D.create(Punto2D.create(), Punto2D.create(-1., 1.));
		Recta2D rm = Recta2D.create(Punto2D.create(2., 2.),Punto2D.create(-1.,-1.));
		Recta2D r1 = r.getParalela(Punto2D.create(0.,1.));
		Recta2D r2 = r.getPerpendicular(Punto2D.create(1.,1.));	
		Recta2D r0 = r.rota(Punto2D.getOrigen(), Math.PI/2);
		Vector2D v1 = Vector2D.createCartesiano(1., 1.);
		Vector2D v2 = Vector2D.createCartesiano(-1., -1.);
		Vector2D v3 = Vector2D.createCartesiano(1., 0.);
		System.out.println(r+","+rm+","+r1+","+r2+","+r.equals(rm));
		System.out.println(r.getAnguloEnGrados()-rm.getAnguloEnGrados());
		System.out.println(r.getPunto()+","+r.getVector());
		System.out.println(r0.getPunto()+","+r0.getVector());
		System.out.println(Punto2D.create(1., 1.).rota(Punto2D.getOrigen(), Math.PI/2));
		System.out.println(v3.proyectaSobre(v1)+","+v3.proyectaSobre(v2));
		System.out.println(r.cortaA(rc));
		System.out.println(r.getDistancia(Punto2D.create(-1., 1.)));	
		MarcoDeTrabajo.setMarco(-50., 50., -50., 50.);
		System.out.println("Marco  = "+MarcoDeTrabajo.xMin+","+MarcoDeTrabajo.xMax+","+MarcoDeTrabajo.yMin+","+MarcoDeTrabajo.yMax);
		r = Recta2D.create(Punto2D.create(-400.,-400.), Punto2D.create(400., 400.));
//		Semiplano2D s = Semiplano2D.create(r,Punto2D.create(-100.,100.));
		MarcoDeTrabajo.objetos.add(Punto2D.create(0., 0.));
		MarcoDeTrabajo.objetos.add(Punto2D.create(-200., -300.));
//		MarcoDeTrabajo.objetos.add(s);
		MarcoDeTrabajo.objetos.add(Recta2D.create(
				Punto2D.create(MarcoDeTrabajo.xMin, MarcoDeTrabajo.centroY),
				Punto2D.create(MarcoDeTrabajo.xMax, MarcoDeTrabajo.centroY)));
		MarcoDeTrabajo.objetos.add(Recta2D.create(
				Punto2D.create(MarcoDeTrabajo.centroX, MarcoDeTrabajo.yMin),
				Punto2D.create(MarcoDeTrabajo.centroX, MarcoDeTrabajo.yMax)));
//		System.out.println(r.getPunto()+","+r.getPunto().add(r.getVector()));
		MarcoDeTrabajo.objetos.add(r);
		
/*		Recta2D r5 = Recta2D.create(Punto2D.create(1., 1.), Vector2D.createCartesiano(1., 1.));
		Semiplano2D s1 = Semiplano2D.create(r5, Punto2D.create(-10.,10.));
		MarcoDeTrabajo.objetos.add(s1);
*/		MarcoDeTrabajo.muestra();
	}

}
