package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("itinerarioHasOrdineHome")
public class ItinerarioHasOrdineHome extends EntityHome<ItinerarioHasOrdine> {

	public void setItinerarioHasOrdineId(ItinerarioHasOrdineId id) {
		setId(id);
	}

	public ItinerarioHasOrdineId getItinerarioHasOrdineId() {
		return (ItinerarioHasOrdineId) getId();
	}

	public ItinerarioHasOrdineHome() {
		setItinerarioHasOrdineId(new ItinerarioHasOrdineId());
	}

	@Override
	public boolean isIdDefined() {
		if (getItinerarioHasOrdineId().getIdordine() == 0)
			return false;
		if (getItinerarioHasOrdineId().getIditinerario() == 0)
			return false;
		return true;
	}

	@Override
	protected ItinerarioHasOrdine createInstance() {
		ItinerarioHasOrdine itinerarioHasOrdine = new ItinerarioHasOrdine();
		itinerarioHasOrdine.setId(new ItinerarioHasOrdineId());
		return itinerarioHasOrdine;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public ItinerarioHasOrdine getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
