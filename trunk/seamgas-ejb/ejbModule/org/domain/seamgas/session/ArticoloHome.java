package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import java.util.ArrayList;
import java.util.List;
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

	public List<Ordine> getOrdines() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines());
	}

	public List<Cybercontadino> getCybercontadinos() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos());
	}

}
