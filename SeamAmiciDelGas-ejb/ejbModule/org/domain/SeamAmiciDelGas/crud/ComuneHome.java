package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("comuneHome")
public class ComuneHome extends EntityHome<Comune> {

	@In(create = true)
	ProvincesHome provincesHome;

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
		Provinces provinces = provincesHome.getDefinedInstance();
		if (provinces != null) {
			getInstance().setProvinces(provinces);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Comune getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Utente> getUtentesForIdcomune() {
		return getInstance() == null ? null : new ArrayList<Utente>(
				getInstance().getUtentesForIdcomune());
	}

	public List<Cybercontadino> getCybercontadinos() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos());
	}

	public List<Utente> getUtentesForComuneNascita() {
		return getInstance() == null ? null : new ArrayList<Utente>(
				getInstance().getUtentesForComuneNascita());
	}

	public List<PuntiDiConsegna> getPuntiDiConsegnas() {
		return getInstance() == null ? null : new ArrayList<PuntiDiConsegna>(
				getInstance().getPuntiDiConsegnas());
	}

}
