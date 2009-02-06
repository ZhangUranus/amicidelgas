package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("patenteHome")
public class PatenteHome extends EntityHome<Patente> {

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
	}

	public boolean isWired() {
		return true;
	}

	public Patente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
