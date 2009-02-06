package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("itinerarioHome")
public class ItinerarioHome extends EntityHome<Itinerario> {

	public void setItinerarioIditinerario(Integer id) {
		setId(id);
	}

	public Integer getItinerarioIditinerario() {
		return (Integer) getId();
	}

	@Override
	protected Itinerario createInstance() {
		Itinerario itinerario = new Itinerario();
		return itinerario;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Itinerario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
