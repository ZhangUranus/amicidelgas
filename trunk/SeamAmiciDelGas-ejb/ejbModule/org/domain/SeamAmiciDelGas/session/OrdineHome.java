package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("ordineHome")
public class OrdineHome extends EntityHome<Ordine> {

	public void setOrdineIdordine(Integer id) {
		setId(id);
	}

	public Integer getOrdineIdordine() {
		return (Integer) getId();
	}

	@Override
	protected Ordine createInstance() {
		Ordine ordine = new Ordine();
		return ordine;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Ordine getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
