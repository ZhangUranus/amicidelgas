package org.domain.SeamAmiciDelGas.session;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.domain.SeamAmiciDelGas.crud.AccountHome;
import org.domain.SeamAmiciDelGas.crud.AccountList;
import org.domain.SeamAmiciDelGas.crud.CybercontadinoList;
import org.domain.SeamAmiciDelGas.crud.RoleList;
import org.domain.SeamAmiciDelGas.crud.UtenteHome;
import org.domain.SeamAmiciDelGas.crud.UtenteList;
import org.domain.SeamAmiciDelGas.entity.Account;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.entity.Utente;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Name(value="gestioneEditUtentiBean")
@Scope(ScopeType.SESSION)
public class GestioneEditUtentiBean {

	@Logger
	private Log log;

	@In(value="accountList",create=true)
	private AccountList accountList;
	
	@In(value="utenteList", create=true)
	private UtenteList utenteList;

	private List<Account> accountFiltredList;

	private List<Cybercontadino> cyberFiltredList;
	
	private boolean nullBool = true;
	
	public List<Account> getAccountFiltredList() {
		
		accountList.setEjbql("select account from Account account where account.utente is not null ");
		String s = "%";
		String[] RESTRICTIONS = {
				"lower(account.username) like concat(lower(#{gestioneEditUtentiBean.accountList.account.username}),'%')",
				"lower(utente.cognome) like concat(lower(#{gestioneEditUtentiBean.utenteList.utente.cognome}),'%')",
				"lower(utente.nome) like concat(lower(#{gestioneEditUtentiBean.utenteList.utente.nome}),'%')",
				"account.punteggioFeedback >= #{gestioneEditUtentiBean.accountList.account.punteggioFeedback}",};
				
		accountList.setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		accountFiltredList = accountList.getResultList();
		return accountFiltredList;
	}

	//metodi get e set 
	
	public AccountList getAccountList() {
		return accountList;
	}

	public void setAccountList(AccountList accountList) {
		this.accountList = accountList;
	}

	public void setAccountFiltredList(List<Account> accountFiltredList) {
		this.accountFiltredList = accountFiltredList;
	}

	public UtenteList getUtenteList() {
		return utenteList;
	}

	public void setUtenteList(UtenteList utenteList) {
		this.utenteList = utenteList;
	}

	public boolean isNullBool() {
		return nullBool;
	}

	public void setNullBool(boolean nullBool) {
		this.nullBool = nullBool;
	}
	
}
