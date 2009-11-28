package org.domain.GAS.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.domain.GAS.session.SearchArticolo;
import org.domain.GAS.session.SearchContadino;
import org.domain.GAS.session.SearchOption;
import org.domain.GAS.session.SearchUtente;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

//@org.jboss.seam.annotations.faces.Converter
//@Name("searchOptionConverter")
//@BypassInterceptors
public class SearchOptionConverter { /* implements Converter{
	
	public SearchOptionConverter(){}
	
	 public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		String label=arg2;
		if(label.equals("Utente"))
			return new SearchUtente();
		else if (label.equals("Contadino")) 
			return new SearchContadino();
		else if (label.equals("Articolo"))
			return new SearchArticolo();
		else
			return null;
	}

		
		public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			SearchOption s= (SearchOption) arg2;
			return s.getLabel();
		} */
}
