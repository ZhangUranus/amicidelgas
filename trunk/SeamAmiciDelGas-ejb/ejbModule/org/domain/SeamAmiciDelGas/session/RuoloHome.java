package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("ruoloHome")
public class RuoloHome extends EntityHome<Ruolo> {

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
	}

	public boolean isWired() {
		return true;
	}

	public Ruolo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
