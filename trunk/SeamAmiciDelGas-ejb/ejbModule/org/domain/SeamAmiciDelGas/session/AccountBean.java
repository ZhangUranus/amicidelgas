package org.domain.SeamAmiciDelGas.session;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Utente;

@Name(value="accountBean")
@Scope(ScopeType.SESSION)
public class AccountBean {

	@Logger
	private Log log;
	
	@In(value="accountList",create=true)
	private AccountList accountList;
	
	private Account currentCustomer;
	
	private List<Account> richiesteCustomer;
	
	public List<Account> getRichiesteCustomer() {
		log.info("*****************");
		log.info("******Carico le richieste di registrazione dei customer*****");
		log.info("*****************");
		accountList.setEjbql("select account from Account account where account.attivato='"+false+"'");
		richiesteCustomer = accountList.getResultList();
		return richiesteCustomer;
	}
	
	public void accettaRegistrazioneCustomer() {
		
	}

	public void setRichiesteCustomer(List<Account> richiesteCustomer) {
		this.richiesteCustomer = richiesteCustomer;
	}

	public Account getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Account currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

}
