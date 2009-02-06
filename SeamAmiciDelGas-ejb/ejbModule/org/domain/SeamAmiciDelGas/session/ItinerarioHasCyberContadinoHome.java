package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("itinerarioHasCyberContadinoHome")
public class ItinerarioHasCyberContadinoHome extends
		EntityHome<ItinerarioHasCyberContadino> {

	public void setItinerarioHasCyberContadinoId(
			ItinerarioHasCyberContadinoId id) {
		setId(id);
	}

	public ItinerarioHasCyberContadinoId getItinerarioHasCyberContadinoId() {
		return (ItinerarioHasCyberContadinoId) getId();
	}

	public ItinerarioHasCyberContadinoHome() {
		setItinerarioHasCyberContadinoId(new ItinerarioHasCyberContadinoId());
	}

	@Override
	public boolean isIdDefined() {
		if (getItinerarioHasCyberContadinoId().getIditinerario() == 0)
			return false;
		if (getItinerarioHasCyberContadinoId().getPartitaIva() == 0)
			return false;
		return true;
	}

	@Override
	protected ItinerarioHasCyberContadino createInstance() {
		ItinerarioHasCyberContadino itinerarioHasCyberContadino = new ItinerarioHasCyberContadino();
		itinerarioHasCyberContadino.setId(new ItinerarioHasCyberContadinoId());
		return itinerarioHasCyberContadino;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public ItinerarioHasCyberContadino getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
