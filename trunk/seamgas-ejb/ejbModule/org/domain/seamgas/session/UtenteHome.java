package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("utenteHome")
public class UtenteHome extends EntityHome<Utente> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	ComuneHome comuneHome;

	public void setUtenteCodiceFiscale(String id) {
		setId(id);
	}

	public String getUtenteCodiceFiscale() {
		return (String) getId();
	}

	@Override
	protected Utente createInstance() {
		Utente utente = new Utente();
		return utente;
	}

	public void wire() {
		getInstance();
		Account account = accountHome.getDefinedInstance();
		if (account != null) {
			getInstance().setAccount(account);
		}
		Comune comune = comuneHome.getDefinedInstance();
		if (comune != null) {
			getInstance().setComune(comune);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccount() == null)
			return false;
		if (getInstance().getComune() == null)
			return false;
		return true;
	}

	public Utente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Patente> getPatentes() {
		return getInstance() == null ? null : new ArrayList<Patente>(
				getInstance().getPatentes());
	}

}
