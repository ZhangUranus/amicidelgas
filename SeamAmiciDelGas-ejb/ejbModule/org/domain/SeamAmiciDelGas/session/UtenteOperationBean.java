package org.domain.SeamAmiciDelGas.session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.international.StatusMessages;


@Name("utenteOperation")
@Scope(ScopeType.SESSION)

public class UtenteOperationBean
{
    @Logger private Log log;

    @In  private Credentials credentials;
    
    @In StatusMessages statusMessages;
    
    private MyObject myObject;
    private List<MyObject> dati;
    
    @In(value="accountList",create=true)
	private AccountList accountList;


	public List<MyObject> getDati() {
		accountList.setEjbql("select account from Account account where account.username='"+credentials.getUsername()+"'");
		log.info("******* booooo *********");
		List<Account> accounts = accountList.getResultList();
		if (accounts==null)
			log.info("******* Accounts è null ******");
		Account cc = accounts.get(0);
		if (cc==null)
			log.info("********** cc è null **********");
		Account account = accountList.getAccount();
		if (account==null)
			log.info("Account è null");
		log.info("******"+account.getUsername()+"******");
		//Iterator<Account> iterator = accounts.iterator();
		//Account account = iterator.next();
		Utente utente = account.getUtente();
		if (utente==null)
			log.info("l'utente non è settato");
		ArrayList<MyObject> dati = new ArrayList<MyObject>();
		MyObject o1 = new MyObject();
		o1.setCampo("Codice Fiscale");
		o1.setValue(utente.getCodiceFiscale());
		dati.add(o1);
		MyObject o2 = new MyObject();
		o2.setCampo("Cognome");
		o2.setValue(utente.getCognome());
		dati.add(o2);
		MyObject o3 = new MyObject();
		o3.setCampo("Nome");
		o3.setValue(utente.getNome());
		dati.add(o3);
		MyObject o4 = new MyObject();
		o4.setCampo("Comune di nascita");
		o4.setValue(utente.getComuneByComuneNascita().getNome());
		dati.add(o4);
		MyObject o5 = new MyObject();
		o5.setCampo("Comune di residenza");
		o5.setValue(utente.getComuneByIdcomune().getNome());
		dati.add(o5);
		MyObject o6 = new MyObject();
		o6.setCampo("Indirizzo");
		o6.setValue(utente.getIndirizzo());
		dati.add(o6);
		MyObject o7 = new MyObject();
		o7.setCampo("Sesso");
		o7.setValue("sesso");
		dati.add(o7);
		MyObject o8 = new MyObject();
		o8.setCampo("Recapito telefonico");
		o8.setValue(utente.getRecapitoTelefonico());
		dati.add(o8);
		MyObject o9 = new MyObject();
		o9.setCampo("Email");
		o9.setValue(utente.getEmail());
		dati.add(o9);
		return dati;
	}
	
	public class MyObject {
		
		private String campo;
		private String value;

		public String getCampo() {
			return campo;
		}

		public void setCampo(String campo) {
			this.campo = campo;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		
		}

	public MyObject getMyObject() {
		return myObject;
	}

	public void setMyObject(MyObject myObject) {
		this.myObject = myObject;
	}

	public void setDati(List<MyObject> dati) {
		this.dati = dati;
	}

	
		
}


