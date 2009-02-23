package org.domain.SeamAmiciDelGas.validator;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.validator.Validator;


//La classe validator per il campo data nascita


public class DataValidator implements Validator, Serializable {

	private static final long serialVersionUID = -1458203631809206211L;
	
	public void initialize(Annotation arg0) {		
	}
	
	public boolean isValid(Object value) {
		
		Date dataNascita = (Date) value;
		GregorianCalendar oggi = new GregorianCalendar();
		int giorno = oggi.get(oggi.DAY_OF_MONTH);
		int mese = oggi.get(oggi.MONTH);
		int anno = oggi.get(oggi.YEAR)-18;
		GregorianCalendar newCalendar = new GregorianCalendar(anno, mese, giorno);
		Date newDate = newCalendar.getTime();
		return (newDate.after(dataNascita));

	}


}
