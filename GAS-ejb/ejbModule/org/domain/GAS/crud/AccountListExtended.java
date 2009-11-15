package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;


import java.util.Arrays;
import java.util.List;



@Name("accountListExtended")
public class AccountListExtended extends EntityQuery<Account> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1894107874755486271L;

	private static final String EJBQL = "select account from Account account where account.utente is not null ";
	
	private List<Account> resultListNotEliminato;
	private static final String[] RESTRICTIONS = {
		"lower(account.username) like concat(lower(#{accountListExtended.account.username}),'%')",
		"lower(utente.cognome) like concat(lower(#{utenteList.utente.cognome}),'%')",
		"lower(utente.nome) like concat(lower(#{utenteList.utente.nome}),'%')",
		"account.punteggioFeedback >= #{accountListExtended.account.punteggioFeedback}",};
		
	private Account account = new Account();

	public AccountListExtended() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(100);
	}
	
	public List<Account> returnListAccount()
	{
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(100);
		return getResultList();
	}
	
	public List<Account> returnListAccountNonEliminato()
	{
		String EJBQL2 = "select account from Account account where account.utente is not null and account.eliminato=false";
		
		setEjbql(EJBQL2);
		String[] RESTRICTIONS2 = {
				"lower(account.username) like concat(lower(#{accountListExtended.account.username}),'%')",
				"lower(utente.cognome) like concat(lower(#{utenteList.utente.cognome}),'%')",
				"lower(utente.nome) like concat(lower(#{utenteList.utente.nome}),'%')",};
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS2));
		setMaxResults(100);
		return getResultList();
	}
	
	
	public Account getAccount() {
		return account;
	}

	public List<Account> getResultListNotEliminato() {
		return returnListAccountNonEliminato();
	}

	public void setResultListNotEliminato(List<Account> resultListNotEliminato) {
		this.resultListNotEliminato = resultListNotEliminato;
	}
}
