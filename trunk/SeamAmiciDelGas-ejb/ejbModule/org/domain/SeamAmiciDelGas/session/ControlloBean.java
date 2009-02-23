package org.domain.SeamAmiciDelGas.session;

import java.util.List;

import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.CybercontadinoList;
import org.domain.SeamAmiciDelGas.crud.UtenteList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name(value="newControlloBean")
@Scope(ScopeType.SESSION)
public class ControlloBean {

	@Logger
	private Log log;
	
	@In 
    StatusMessages statusMessages;
	
	@In(value="newUtente", create=true)
	private Utente utente;
	@In(value="newAccount", create=true)
	private Account account;
	
	@In(value="accountList",create=true)
	private AccountList accountList;
	@In(value="utenteList",create=true)
	private UtenteList utenteList;
	
	@In(value="newCybercontadino", create=true)
	private Cybercontadino contadino;
	
	@In(value="cybercontadinoList",create=true)
	private CybercontadinoList contadinoList;
	
	
	//Controllo se esiste gia' un codice fiscale
	private String myResponseCodiceFiscale;
	
	public void controllaCodiceFiscale()
	{
		//log.info(utente.getCodiceFiscale());
		utenteList.setEjbql("select utente from Utente utente where utente.codiceFiscale='"+utente.getCodiceFiscale()+"'");
		List<Utente> lu = utenteList.getResultList();
		if(!lu.isEmpty())
			myResponseCodiceFiscale = "  Il codice fiscale inserito � gi� presente nel database";
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
			myResponseEmail = "  L'email inserita � gi� presente nel database";
		else
			myResponseEmail  = null;
	}

	public String getMyResponseEmail() {
		return myResponseEmail;
	}

	public void setMyResponseEmail(String myResponseEmail) {
		this.myResponseEmail = myResponseEmail;
	}
	
	/*
	 * Controlla se esiste la user-name
	 */
	private String myResponseUserName;
	
	public void controllaUserName()
	{
		log.info(account.getUsername());
		accountList.setEjbql("select account from Account account where account.username='"+account.getUsername()+"'");
		List<Account> lu = accountList.getResultList();
		if(!lu.isEmpty())
			myResponseUserName = "  Username non disponbile";
		else
			myResponseUserName  = null;
	}

	public String getMyResponseUserName() {
		return myResponseUserName;
	}

	public void setMyResponseUserName(String myResponseUserName) {
		this.myResponseUserName = myResponseUserName;
	}

	
	public String myResponsePartitaIva;
	
	public void controllaPartitaIva()
    {
        // implement your business logic here
        log.info("ControllaParitaIva.controllaParitaIva() action called");
        statusMessages.add("controllaParitaIva");
        contadinoList.setEjbql("select contadino from Cybercontadino contadino where contadino.partitaIva='"+contadino.getPartitaIva()+"'");
		List<Cybercontadino> lu = contadinoList.getResultList();
		if(!lu.isEmpty())
			myResponsePartitaIva = "  La partita Iva inserita � gi� presente nel database";
		else
			myResponsePartitaIva = null;
    }

    // add additional action methods
    
    public String getMyResponsePartitaIva() {
		return myResponsePartitaIva;
	}

	public void setMyResponsePartitaIva(String myResponsePartitaIva) {
		this.myResponsePartitaIva = myResponsePartitaIva;
	}

}