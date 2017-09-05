package us.lsi.concurrent.filosofos;

import org.jdesktop.beansbinding.Converter;

public class ConvertTextToLong extends Converter<String, Long> {

	public ConvertTextToLong() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Long convertForward(String s) {
		// TODO Auto-generated method stub
		return new Long(s);
	}

	@Override
	public String convertReverse(Long e) {
		// TODO Auto-generated method stub
		return e.toString();
	}

}
