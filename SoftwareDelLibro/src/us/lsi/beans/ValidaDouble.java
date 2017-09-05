package us.lsi.beans;

import org.jdesktop.beansbinding.Validator;

public class ValidaDouble extends Validator<Double> {

	@Override
	public org.jdesktop.beansbinding.Validator<Double>.Result validate(Double a) {
		// TODO Auto-generated method stub
		Result r = null;		
		if (a == Double.NEGATIVE_INFINITY) {
			r = new Result(null, "Formato no correcto");
		} else if (a <0.){
			r = new Result(null, "Valor no correcto");
		}
		return r;
	}

}
