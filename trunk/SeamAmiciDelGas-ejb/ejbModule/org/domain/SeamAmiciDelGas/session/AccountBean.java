package org.domain.SeamAmiciDelGas.session;

import java.util.Iterator;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.RoleList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Role;
import org.domain.SeamAmiciDelGas.entity.Utente;

@Name(value="accountBean")
@Scope(ScopeType.SESSION)
public class AccountBean {

	@Logger
	private Log log;
	
	@In(value="accountList",create=true)
	private AccountList accountList;
	
	@In(value="roleList",create=true)
	private RoleList roleList;
	
	private Account currentCustomer;
	
	private List<Account> richiesteCustomer;

	//seleziono gli utenti customer che sono nello stato attivato 0 bloccato 0 eliminato 0
	public List<Account> getRichiesteCustomer() {
		accountList.setEjbql("select account from Role role where role.account.username not in (select role1.account from Role role1 where role1.name='admin' or role1.name='mediatore') and role.account.attivato='false' and role.account.bloccato='false' and role.account.elimato='false'");
		richiesteCustomer = accountList.getResultList();
		return richiesteCustomer;
	}
	
	public void accettaRegistrazioneCustomer() {
		log.info("**********************");
		log.info("********** ha accettato la richiesta**********");
		log.info("**********************");
	}
	
	public void rifiutaRegistrazioneCustomer() {
		log.info("**********************");
		log.info("********** NON ha accettato la richiesta**********");
		log.info("**********************");
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
