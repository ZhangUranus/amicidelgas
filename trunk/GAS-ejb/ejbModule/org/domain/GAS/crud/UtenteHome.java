package org.domain.GAS.crud;

import org.domain.GAS.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;

@Name("utenteHome")
public class UtenteHome extends EntityHome<Utente> {
	
	@Logger private Log log;
	@In(create = true, value = "comuneHome")
	ComuneHome comuneHomeByComuneNascita;
	@In(create = true, value = "comuneHome")
	ComuneHome comuneHomeByIdcomune;

	public void setUtenteCodiceFiscale(String id) {
		setId(id);
	}

	public String getUtenteCodiceFiscale() {
		return (String) getId();
	}

	@Override
	protected Utente createInstance() {
		Utente utente = new Utente();
		return utente;
	}
	
	@Override
	public String remove() {
		log.info("UTENTE HOME id: "+this.getUtenteCodiceFiscale());
		log.info("UTENTE HOME: "+this.getInstance().getCognome());
		Set<Account> accounts = getInstance().getAccounts();
		for(Account a : accounts)
			a.setEliminato(true);
		update();
		return "utente settato a cancellato";
	}


	
	public void wire() {
		getInstance();
		Comune comuneByComuneNascita = comuneHomeByComuneNascita
				.getDefinedInstance();
		if (comuneByComuneNascita != null) {
			getInstance().setComuneByComuneNascita(comuneByComuneNascita);
		}
		Comune comuneByIdcomune = comuneHomeByIdcomune.getDefinedInstance();
		if (comuneByIdcomune != null) {
			getInstance().setComuneByIdcomune(comuneByIdcomune);
		}
	}

	public boolean isWired() {
		if (getInstance().getComuneByComuneNascita() == null)
			return false;
		if (getInstance().getComuneByIdcomune() == null)
			return false;
		return true;
	}
	
	public Utente getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Account> getAccounts() {
		return getInstance() == null ? null : new ArrayList<Account>(
				getInstance().getAccounts());
	}

	public List<Patente> getPatentes() {
		return getInstance() == null ? null : new ArrayList<Patente>(
				getInstance().getPatentes());
	}


}
