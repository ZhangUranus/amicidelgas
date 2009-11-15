package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("itinerarioHome")
public class ItinerarioHome extends EntityHome<Itinerario> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5752988522415562199L;
	@In(create = true)
	AccountHome accountHome;

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
		Account account = accountHome.getDefinedInstance();
		if (account != null) {
			getInstance().setAccount(account);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccount() == null)
			return false;
		return true;
	}

	public Itinerario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
