package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;


import java.util.Arrays;


@Name("accountListExtended")
public class AccountListExtended extends EntityQuery<Account> {

	//private static final String EJBQL = "select account from Account account where account.utente is not null ";
	private static final String EJBQL = "select account from Account account";

	private static final String[] RESTRICTIONS = {
		"lower(account.username) like concat(lower(#{accountListExtended.account.username}),'%')",};
		//"lower(utente.cognome) like concat(lower(#{utenteList.utente.cognome}),'%')",};
		//"lower(utente.nome) like concat(lower(#{gestioneEditUtentiBean.utenteList.utente.nome}),'%')",
		//"account.punteggioFeedback >= #{accountListExtended.account.punteggioFeedback}",};
		
	private Account account = new Account();

	public AccountListExtended() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(100);
	}
	
	public Account getAccount() {
		return account;
	}
}
