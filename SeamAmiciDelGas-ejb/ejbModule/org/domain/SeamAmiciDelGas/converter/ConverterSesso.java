package org.domain.SeamAmiciDelGas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class ConverterSesso implements Converter {

	public ConverterSesso(){}
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

		return null;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		try {	
			Boolean b = (Boolean) arg2;
			if(b)
				return "Maschio";
			return "Femmina";
		}
	      catch (Exception exception) {
	          throw new ConverterException(exception);
	          }
	}

}
