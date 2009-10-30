package org.domain.GAS.crud;

import org.domain.GAS.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

@Name("patenteHome")
public class PatenteHome extends EntityHome<Patente> {

	@In(create = true)
	UtenteHome utenteHome;
	
	@Logger private Log log;

	public void setPatenteIdpatente(Integer id) {
		setId(id);
	}

	public Integer getPatenteIdpatente() {
		return (Integer) getId();
	}

	@Override
	public String remove() {
		log.info("PATENTE HOME id: "+this.getPatenteIdpatente());
		log.info("PATENTE HOME: "+this.getInstance().getTipo());
		utenteHome.getInstance().getPatentes().remove(this.getInstance());
		return super.remove();
	}
	
	
	@Override
	protected Patente createInstance() {
		Patente patente = new Patente();
		return patente;
	}

	public void wire() {
		getInstance();
		Utente utente = utenteHome.getDefinedInstance();
		if (utente != null) {
			getInstance().setUtente(utente);
		}
	}

	public boolean isWired() {
		if (getInstance().getUtente() == null)
			return false;
		return true;
	}

	public Patente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
