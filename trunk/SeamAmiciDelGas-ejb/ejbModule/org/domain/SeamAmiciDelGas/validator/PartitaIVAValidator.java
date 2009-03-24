package org.domain.SeamAmiciDelGas.validator;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.hibernate.validator.Validator;


public class PartitaIVAValidator implements Validator, Serializable
{
	private static final long serialVersionUID = 3599243249440591195L;

	public void initialize(PartitaIVA arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean isValid(Object value) {
		// TODO Auto-generated method stub
		String pi = (String) value;
		if(pi.length()!= 11)
			return false;
		int i,c,s=0;
		for( i=0; i<=9; i+=2 )
			s += pi.charAt(i) - '0';
		for( i=1; i<=9; i+=2 ){
			c = 2*( pi.charAt(i) - '0' );
			if( c > 9 )  c = c - 9;
			s += c;
		}
		if( ( 10 - s%10 )%10 != pi.charAt(10) - '0' )
			return false;
		return true;
	}

	public void initialize(Annotation arg0) {
		// TODO Auto-generated method stub
		
	}

}
