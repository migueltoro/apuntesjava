package us.lsi.concurrent.filosofos;

import org.jdesktop.beansbinding.Converter;


public class ConvertEstTenToTex extends Converter<Tenedor.Estado, String> {

	
	public ConvertEstTenToTex() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String convertForward(Tenedor.Estado e) {
		String r = " O";
		if(e == Tenedor.Estado.Libre){
			r = " L";
		}
		return r;
	}

	@Override
	public Tenedor.Estado convertReverse(String s) {
		Tenedor.Estado r = Tenedor.Estado.Libre;
		if(s.equals(" O")){
			r = Tenedor.Estado.Ocupado;
		}
		return r;
	}

}
