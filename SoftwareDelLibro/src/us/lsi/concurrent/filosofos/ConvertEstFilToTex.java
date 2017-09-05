package us.lsi.concurrent.filosofos;

import org.jdesktop.beansbinding.Converter;


public class ConvertEstFilToTex extends Converter<Filosofo.Estado, String> {

	public ConvertEstFilToTex() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertForward(Filosofo.Estado e) {
		// TODO Auto-generated method stub
		String r = null;
		switch(e){
		case Pensando: r = " P"; break;
		case Esperando: r = " E"; break;
		case Comiendo: r = " C"; break;
		case Fin: r ="F";
		}
		return r;
	}

	@Override
	public Filosofo.Estado convertReverse(String s) {
		// TODO Auto-generated method stub
		Filosofo.Estado r = null;
		if(s.equals(" P")){
			r = Filosofo.Estado.Pensando;
		} else if(s.equals(" C")){
			r = Filosofo.Estado.Comiendo;
		} else if(s.equals(" E")){
			r = Filosofo.Estado.Esperando;
		} else {
			r = Filosofo.Estado.Fin;
		}
		return r;
	}

}
