package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("utenteHome")
public class UtenteHome extends EntityHome<Utente> {

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
	}

	public boolean isWired() {
		return true;
	}

	public Utente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
