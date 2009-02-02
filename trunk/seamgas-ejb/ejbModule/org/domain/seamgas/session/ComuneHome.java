package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import java.util.ArrayList;
import java.util.List;
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

	public List<Utente> getUtentes() {
		return getInstance() == null ? null : new ArrayList<Utente>(
				getInstance().getUtentes());
	}

	public List<Cybercontadino> getCybercontadinos() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos());
	}

	public List<PuntiDiConsegna> getPuntiDiConsegnas() {
		return getInstance() == null ? null : new ArrayList<PuntiDiConsegna>(
				getInstance().getPuntiDiConsegnas());
	}

}
