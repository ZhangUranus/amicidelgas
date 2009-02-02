package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("ruoloHome")
public class RuoloHome extends EntityHome<Ruolo> {

	@In(create = true)
	AccountHome accountHome;

	public void setRuoloIdruolo(Integer id) {
		setId(id);
	}

	public Integer getRuoloIdruolo() {
		return (Integer) getId();
	}

	@Override
	protected Ruolo createInstance() {
		Ruolo ruolo = new Ruolo();
		return ruolo;
	}

	public void wire() {
		getInstance();
		Account account = accountHome.getDefinedInstance();
		if (account != null) {
			getInstance().setAccount(account);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccount() == null)
			return false;
		return true;
	}

	public Ruolo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
