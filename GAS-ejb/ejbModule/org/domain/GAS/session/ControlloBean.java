package org.domain.GAS.session;

import java.util.List;

import org.domain.GAS.crud.AccountList;
import org.domain.GAS.crud.ComuneList;
import org.domain.GAS.crud.CybercontadinoList;
import org.domain.GAS.crud.UtenteList;
import org.domain.GAS.entity.Account;
import org.domain.GAS.entity.Cybercontadino;
import org.domain.GAS.entity.PuntiDiConsegna;
import org.domain.GAS.entity.Utente;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;

@Name(value="newControlloBean")
@Scope(ScopeType.CONVERSATION)
public class ControlloBean {

	@Logger
	private Log log;	
	@In(value="newUtente", create=true)
	private Utente utente;
	@In(value="newAccount", create=true)
	private Account account;
	
	@In(value="accountList",create=true)
	private AccountList accountList;
	@In(value="utenteList",create=true)
	private UtenteList utenteList;
	
	@In(value="newComuneProvinciaBean" , create=true)
	private ComuneProvinciaBean comuneProvincia;
	
	@In(value="newComuneProvinciaResidenzaBean" , create=true)
	private ComuneProvinciaBean comuneProvinciaResidenza;
	
	@In(value="newCybercontadino", create=true)
	private Cybercontadino contadino;
	
	@In(value="newPuntiDiConsegna", create=true)
	private PuntiDiConsegna newPunto;
	
	@In(value="cybercontadinoList",create=true)
	private CybercontadinoList contadinoList;
	
	//Controllo se esiste gia' un codice fiscale
	private String myResponseCodiceFiscale;
	
	private String myResponseComune;
	
	private String myResponseComuneSede;
	
	private String myResponseComuneResidenza;
	
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
		accountList.setEjbql("select account from Account account where account.username='"+account.getUsername()+"'");
		List<Account> lu = accountList.getResultList();
		if(!lu.isEmpty())
		{
			myResponseUserName = "  Username non disponbile";
		}
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
	
	//se i dati come codice fiscale ed email sono gia' presenti
	//nel database non faccio procedere al passo 2 della registrazione
	public String controlloPresenzaDatiDatabase()
	{
		if(comuneProvincia.getComune() == null)
			myResponseComune = " Il comune di nascita non esiste";
		else
			myResponseComune = null;
		if(comuneProvinciaResidenza.getComune() == null)
			myResponseComuneResidenza = " Il comune di residenza non esiste";
		else
			myResponseComuneResidenza = null;
		if(myResponseCodiceFiscale==null && myResponseEmail==null  && myResponseComune == null && myResponseComuneResidenza == null)
			return "passo1Outcome"; //posso procedere con la registrazione passo2 :-)
		else
			return null;//uno dei due campi o tutti e due sono gi� presenti del database
			
	}
	
	public String action()
	{
		if(comuneProvincia.getComune() == null)
			myResponseComuneSede = " Il comune non esiste";
		else
			myResponseComuneSede = null;
		
		if(myResponsePartitaIva==null && myResponseComuneSede == null)
			return "passo1Outcome"; //posso procedere con la registrazione passo2 :-)
		else
			return null;//uno dei due campi o tutti e due sono gi� presenti del database
			
	}

	public void setMyResponseComune(String myResponseComune) {
		this.myResponseComune = myResponseComune;
	}

	public String getMyResponseComune() {
		return myResponseComune;
	}

	public String getMyResponseComuneResidenza() {
		return myResponseComuneResidenza;
	}

	public void setMyResponseComuneResidenza(String myResponseComuneResidenza) {
		this.myResponseComuneResidenza = myResponseComuneResidenza;
	}

	public String getMyResponseComuneSede() {
		return myResponseComuneSede;
	}

	public void setMyResponseComuneSede(String myResponseComuneSede) {
		this.myResponseComuneSede = myResponseComuneSede;
	}

}
