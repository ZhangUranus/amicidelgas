package org.domain.GAS.crud;

import org.domain.GAS.entity.*;

import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("articoloHome")
public class ArticoloHome extends EntityHome<Articolo> {

	@In(create = true)
	CybercontadinoHome cybercontadinoHome;
	@In(create = true)
	OrdineHome ordineHome;
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
		Ordine ordine = ordineHome.getDefinedInstance();
		if (ordine != null) {
			getInstance().setOrdine(ordine);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Articolo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}


}
