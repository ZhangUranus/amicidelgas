package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("puntiDiConsegnaHome")
public class PuntiDiConsegnaHome extends EntityHome<PuntiDiConsegna> {

	@In(create = true)
	ComuneHome comuneHome;

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
		Comune comune = comuneHome.getDefinedInstance();
		if (comune != null) {
			getInstance().setComune(comune);
		}
	}

	public boolean isWired() {
		if (getInstance().getComune() == null)
			return false;
		return true;
	}

	public PuntiDiConsegna getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
