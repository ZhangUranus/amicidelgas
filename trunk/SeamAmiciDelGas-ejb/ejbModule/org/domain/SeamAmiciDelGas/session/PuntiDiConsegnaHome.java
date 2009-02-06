package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("puntiDiConsegnaHome")
public class PuntiDiConsegnaHome extends EntityHome<PuntiDiConsegna> {

	public void setPuntiDiConsegnaIdpuntiConsegna(Integer id) {
		setId(id);
	}

	public Integer getPuntiDiConsegnaIdpuntiConsegna() {
		return (Integer) getId();
	}

	@Override
	protected PuntiDiConsegna createInstance() {
		PuntiDiConsegna puntiDiConsegna = new PuntiDiConsegna();
		return puntiDiConsegna;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public PuntiDiConsegna getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
