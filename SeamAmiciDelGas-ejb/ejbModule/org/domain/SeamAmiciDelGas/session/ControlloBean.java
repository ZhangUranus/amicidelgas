package org.domain.SeamAmiciDelGas.session;

import java.util.List;

import org.domain.SeamAmiciDelGas.crud.UtenteList;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name(value="newControlloBean")
@Scope(ScopeType.SESSION)
public class ControlloBean {

	@Logger
	private Log log;
	
	@In(value="newUtente")
	private Utente utente;
	
	@In(value="utenteList",create=true)
	private UtenteList utenteList;
	
	
	//Controllo se esiste gia' un codice fiscale
	private String myResponseCodiceFiscale;
	
	public void controllaCodiceFiscale()
	{
		//log.info(utente.getCodiceFiscale());
		utenteList.setEjbql("select utente from Utente utente where utente.codiceFiscale='"+utente.getCodiceFiscale()+"'");
		List<Utente> lu = utenteList.getResultList();
		if(!lu.isEmpty())
			myResponseCodiceFiscale = "  Il codice fiscale inserito è già presente nel database";
		else
			myResponseCodiceFiscale  = null;
	}

	public String getMyResponseCodiceFiscale() {
		return myResponseCodiceFiscale;
	}

	public void setMyResponseCodiceFiscale(String myResponseCodiceFiscale) {
		this.myResponseCodiceFiscale = myResponseCodiceFiscale;
	}
	
	
	//Controllo se esiste gia' una email
	private String myResponseEmail;
	
	public void controllaEmail()
	{
		log.info(utente.getEmail());
		utenteList.setEjbql("select utente from Utente utente where utente.email='"+utente.getEmail()+"'");
		List<Utente> lu = utenteList.getResultList();
		if(!lu.isEmpty())
			myResponseEmail = "  L'email inserita è già presente nel database";
		else
			myResponseEmail  = null;
	}

	public String getMyResponseEmail() {
		return myResponseEmail;
	}

	public void setMyResponseEmail(String myResponseEmail) {
		this.myResponseEmail = myResponseEmail;
	}

}
