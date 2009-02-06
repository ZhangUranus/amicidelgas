package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("accountHome")
public class AccountHome extends EntityHome<Account> {

	public void setAccountUsername(String id) {
		setId(id);
	}

	public String getAccountUsername() {
		return (String) getId();
	}

	@Override
	protected Account createInstance() {
		Account account = new Account();
		return account;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Account getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Questionario> getQuestionarios() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios());
	}

}
