package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;

import java.util.Arrays;


@Name("accountList")
public class AccountList extends EntityQuery<Account> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5215142014751622974L;

	private static final String EJBQL = "select account from Account account";

	private static final String[] RESTRICTIONS = {
			"lower(account.username) like concat(lower(#{accountList.account.username}),'%')",
			"lower(account.passwordHash) like concat(lower(#{accountList.account.passwordHash}),'%')",};

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
