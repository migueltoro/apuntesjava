package us.lsi.dyv.puntos;

import java.util.*;

import us.lsi.geometria.ParDePuntos;
import us.lsi.geometria.Punto2D;
import us.lsi.math.Math2;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;



public class ListasDePuntos {
	
	public static Set<Punto2D> puntosMaximales(List<Punto2D> lista){
		Ordering<Punto2D> ord = new Ordering<Punto2D>(){
			@Override
			public int compare(Punto2D p1, Punto2D p2) {
				// TODO Auto-generated method stub
				return p1.getX().compareTo(p2.getX());
			}
			
		};
		Collections.sort(lista, ord);
		return  puntosMaximalesGeneralizado(0,lista.size(),lista);
	}
	
	private static Set<Punto2D> puntosMaximalesGeneralizado(int i, int j, List<Punto2D> lista){
		Preconditions.checkArgument(j>=i);
		Set<Punto2D> r;
		Set<Punto2D> ri;
		Set<Punto2D> rd;
		int k;
		if(j-i<=4){
			r = puntosMaximalesBase(i,j,lista);
		}else{
			k = (i+j)/2;
			ri = puntosMaximalesGeneralizado(i,k,lista);
			rd = puntosMaximalesGeneralizado(k,j,lista);
			r = puntosMaximalesCombina(ri,rd);
		}
		return r;
	}
	
	public static Set<Punto2D> puntosMaximalesBase(int a, int b, List<Punto2D> lista){
		Set<Punto2D> r = Sets.newHashSet();
		Punto2D pi;
		Punto2D pj;
		boolean piEsDominado;
		for(int i = a; i < b; i++){
			pi = lista.get(i);	
			piEsDominado = false;
			for(int j=a;j<b;j++){				
					pj = lista.get(j);
					if(pj.dominaA(pi)){
						piEsDominado = true;
						break;
					}
			}
			if(!piEsDominado){
				r.add(pi);
			}
		}
		return r;
	}
	
	private static Set<Punto2D> puntosMaximalesCombina(Set<Punto2D> si, Set<Punto2D> sd){
		Double maxYD = Double.MIN_VALUE;
		Set<Punto2D> r = Sets.newHashSet(sd);
		for(Punto2D p:sd){
			if(p.getY()>maxYD){
				maxYD = p.getY();
			}
		}
		for(Punto2D p:si){
			if(p.getY()> maxYD){
				r.add(p);
			}
		}
		return r;
	}
	
	public static ParDePuntos parMasCercano(List<Punto2D> lista){
		ParDePuntos p = null;
		List<Punto2D> puntosX;
		List<Punto2D> puntosY;
		Ordering<Punto2D> ordX = new Ordering<Punto2D>(){
			@Override
			public int compare(Punto2D p1, Punto2D p2) {
				return p1.getX().compareTo(p2.getX());
			}
			
		};
		Ordering<Punto2D> ordY = 	new Ordering<Punto2D>(){
			@Override
			public int compare(Punto2D p1, Punto2D p2) {
				return p1.getY().compareTo(p2.getY());
			}
			
		};
		Ordering<ParDePuntos> ordNatural =  Ordering.<ParDePuntos>natural().nullsLast();
		puntosX = ordX.sortedCopy(lista);
		puntosY = ordY.sortedCopy(lista);
		p = masCercano(0,lista.size(),puntosX, puntosY,4,ordNatural);
		return p;
	}
	
	private static ParDePuntos masCercano(int i, int j, List<Punto2D> puntosX, List<Punto2D> puntosY, int umbral, Ordering<ParDePuntos> ordNatural){
		ParDePuntos r = null;
		int k = (i+j)/2;
		if(j-i <= umbral){
			r = parMasCercanoBase(i,j,puntosX);
		}else{
			List<Punto2D> puntosYIzq = Lists.newArrayList();
			List<Punto2D> puntosYDer = Lists.newArrayList();
			Double xk = puntosX.get(k).getX();
			for(Punto2D p:puntosY){
				if(p.getX() < xk){
					puntosYIzq.add(p);
				}else{
					puntosYDer.add(p);
				}
			}
			ParDePuntos s1 = masCercano(i,k,puntosX, puntosYIzq,umbral,ordNatural);
			ParDePuntos s2 = masCercano(k,j,puntosX, puntosYDer,umbral,ordNatural);
			r = ordNatural.min(s1, s2);
			List<Punto2D> yCentral = Lists.newArrayList();
			for(Punto2D p: puntosY){
				if(Math.abs(p.getX()- xk) < r.getDistancia()){
						yCentral.add(p);
				}
			}
			if(!yCentral.isEmpty()){
				r = masCercanoCentral(r, yCentral, ordNatural);	
			}
		}
		return r;
	}
	
	
	
	private static ParDePuntos masCercanoCentral(ParDePuntos s, List<Punto2D> yCentral, Ordering<ParDePuntos> ordNatural) {	
		ParDePuntos r = null;
		double d = s.getDistancia();
		Punto2D pIzq = null;
		Punto2D pDer = null;
		for(int i=0; i < yCentral.size(); i++){
			pIzq = yCentral.get(i);
			for(int j=i+1;j<yCentral.size();j++){
				pDer = yCentral.get(j);
				r = ParDePuntos.create(pIzq, pDer);
				if(r.getDistancia()>d){
					break;
				}
				s = ordNatural.min(s, r) ;
				d = s.getDistancia();
			}
			for(int j=i-1;j>=0;j--){
				pDer = yCentral.get(j);
				r = ParDePuntos.create(pIzq, pDer);
				if(r.getDistancia()>d){
					break;
				}
				s = ordNatural.min(s, r) ;
				d = s.getDistancia();
			}
		}
		return s;
	}

	public static ParDePuntos parMasCercanoBase(int a, int b, List<Punto2D> lista){
		ParDePuntos p = null;
		Double r = Double.MAX_VALUE;
		Punto2D p1;
		Punto2D p2;
		Double d;
		for(int i = a; i < b; i++){
			for(int j = i+1; j < b; j++){
				p1 = lista.get(i);
				p2 = lista.get(j);
				d = p1.getDistanciaA(p2);
				if(d < r ){
					r = d;
					p = ParDePuntos.create(p1, p2);
				}
			}
		}
		return p;
	}
	
	public static List<Punto2D> getListaPuntosAleatoria(int n){
		List<Punto2D> r = Lists.newArrayList();
		for(int i=0; i < n; i++){
			r.add(Punto2D.create(Math2.getDoubleAleatorio(-1000., 1000.),Math2.getDoubleAleatorio(-1000., 1000.)));
		}
		return r;
	}
	
}
