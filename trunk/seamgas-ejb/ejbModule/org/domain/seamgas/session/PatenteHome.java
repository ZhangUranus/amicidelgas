package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("patenteHome")
public class PatenteHome extends EntityHome<Patente> {

	@In(create = true)
	UtenteHome utenteHome;

	public void setPatenteIdpatente(Integer id) {
		setId(id);
	}

	public Integer getPatenteIdpatente() {
		return (Integer) getId();
	}

	@Override
	protected Patente createInstance() {
		Patente patente = new Patente();
		return patente;
	}

	public void wire() {
		getInstance();
		Utente utente = utenteHome.getDefinedInstance();
		if (utente != null) {
			getInstance().setUtente(utente);
		}
	}

	public boolean isWired() {
		if (getInstance().getUtente() == null)
			return false;
		return true;
	}

	public Patente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
