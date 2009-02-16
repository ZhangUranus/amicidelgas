package org.domain.SeamAmiciDelGas.validator;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Date;

import org.domain.SeamAmiciDelGas.entity.Utente;
import org.hibernate.validator.Validator;
import org.jboss.seam.annotations.In;


//La classe validator per il campo data nascita


public class DataValidator implements Validator, Serializable {

	private static final long serialVersionUID = -1458203631809206211L;
	
	public void initialize(Annotation arg0) {		
	}
	
	@SuppressWarnings("deprecation")
	public boolean isValid(Object obj) {
		
		Date dateNascita = (Date) obj;
		Date localDate = new Date();
		localDate.setTime(System.currentTimeMillis());
		
		int annoNascita = dateNascita.getYear();
		int annoLocal = localDate.getYear();

		if((annoLocal-annoNascita >=18)) {
			return true;
		}
		return false;

	}


}
