package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("articoloHome")
public class ArticoloHome extends EntityHome<Articolo> {

	@In(create = true)
	CybercontadinoHome cybercontadinoHome;

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
		Cybercontadino cybercontadino = cybercontadinoHome.getDefinedInstance();
		if (cybercontadino != null) {
			getInstance().setCybercontadino(cybercontadino);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Articolo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Ordine> getOrdines() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines());
	}

	public List<Ordine> getOrdines_1() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines_1());
	}

}
