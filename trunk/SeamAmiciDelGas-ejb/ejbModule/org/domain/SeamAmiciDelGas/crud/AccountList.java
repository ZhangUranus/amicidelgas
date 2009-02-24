package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("accountList")
public class AccountList extends EntityQuery<Account> {

	private static final String EJBQL = "select account from Account account";

	private static final String[] RESTRICTIONS = {
			"lower(account.username) like concat(lower(#{accountList.account.username}),'%')",
			"lower(account.passwordHash) like concat(lower(#{accountList.account.passwordHash}),'%')",
			"lower(utente.cognome) like concat(lower(#{utenteList.utente.cognome}),'%')",
			"lower(utente.nome) like concat(lower(#{utenteList.utente.nome}),'%')",};

	private Account account = new Account();

	public AccountList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Account getAccount() {
		return account;
	}
}
