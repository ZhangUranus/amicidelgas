package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("comuneHome")
public class ComuneHome extends EntityHome<Comune> {

	public void setComuneIdcomune(Integer id) {
		setId(id);
	}

	public Integer getComuneIdcomune() {
		return (Integer) getId();
	}

	@Override
	protected Comune createInstance() {
		Comune comune = new Comune();
		return comune;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Comune getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
