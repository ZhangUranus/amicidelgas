package org.domain.SeamAmiciDelGas.crud;

import java.util.List;

import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name(value="newControllaCodiceFiscaleBean")
@Scope(ScopeType.SESSION)
public class ControllaCodiceFiscaleBean {

	@Logger
	private Log log;
	
	@In(value="newUtente")
	private Utente utente;
	
	@In(value="utenteList",create=true)
	private UtenteList utenteList;
	
	private String myResponse;
	
	public void controllaCodiceFiscale()
	{
		//log.info(utente.getCodiceFiscale());
		utenteList.setEjbql("select utente from Utente utente where utente.codiceFiscale='"+utente.getCodiceFiscale()+"'");
		List<Utente> lu = utenteList.getResultList();
		if(!lu.isEmpty())
			myResponse = "  Il codice fiscale inserito è già presente nel database";
		else
			myResponse = null;
	}

	public String getMyResponse() {
		return myResponse;
	}

	public void setMyResponse(String myResponse) {
		this.myResponse = myResponse;
	}


	
	
	
}
