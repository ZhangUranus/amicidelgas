package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("itinerarioHasPuntiDiConsegnaHome")
public class ItinerarioHasPuntiDiConsegnaHome extends
		EntityHome<ItinerarioHasPuntiDiConsegna> {

	public void setItinerarioHasPuntiDiConsegnaId(
			ItinerarioHasPuntiDiConsegnaId id) {
		setId(id);
	}

	public ItinerarioHasPuntiDiConsegnaId getItinerarioHasPuntiDiConsegnaId() {
		return (ItinerarioHasPuntiDiConsegnaId) getId();
	}

	public ItinerarioHasPuntiDiConsegnaHome() {
		setItinerarioHasPuntiDiConsegnaId(new ItinerarioHasPuntiDiConsegnaId());
	}

	@Override
	public boolean isIdDefined() {
		if (getItinerarioHasPuntiDiConsegnaId().getIdpuntiConsegna() == 0)
			return false;
		if (getItinerarioHasPuntiDiConsegnaId().getIditinerario() == 0)
			return false;
		return true;
	}

	@Override
	protected ItinerarioHasPuntiDiConsegna createInstance() {
		ItinerarioHasPuntiDiConsegna itinerarioHasPuntiDiConsegna = new ItinerarioHasPuntiDiConsegna();
		itinerarioHasPuntiDiConsegna
				.setId(new ItinerarioHasPuntiDiConsegnaId());
		return itinerarioHasPuntiDiConsegna;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public ItinerarioHasPuntiDiConsegna getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
