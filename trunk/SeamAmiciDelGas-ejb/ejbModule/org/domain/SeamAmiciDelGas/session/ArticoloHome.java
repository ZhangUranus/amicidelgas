package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("articoloHome")
public class ArticoloHome extends EntityHome<Articolo> {

	public void setArticoloIdarticolo(Integer id) {
		setId(id);
	}

	public Integer getArticoloIdarticolo() {
		return (Integer) getId();
	}

	@Override
	protected Articolo createInstance() {
		Articolo articolo = new Articolo();
		return articolo;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Articolo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
