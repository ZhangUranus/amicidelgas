package org.domain.seamgas.session;

import org.domain.seamgas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("accountHome")
public class AccountHome extends EntityHome<Account> {

	public void setAccountUsername(String id) {
		setId(id);
	}

	public String getAccountUsername() {
		return (String) getId();
	}

	@Override
	protected Account createInstance() {
		Account account = new Account();
		return account;
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Account getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Ordine> getOrdines() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines());
	}

	public List<Itinerario> getItinerarios() {
		return getInstance() == null ? null : new ArrayList<Itinerario>(
				getInstance().getItinerarios());
	}

	public List<Cybercontadino> getCybercontadinos() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos());
	}

	public List<Ruolo> getRuolos() {
		return getInstance() == null ? null : new ArrayList<Ruolo>(
				getInstance().getRuolos());
	}

	public List<Feedback> getFeedbacksForDestinatario() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForDestinatario());
	}

	public List<Feedback> getFeedbacksForSegnalatore() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForSegnalatore());
	}

	public List<Utente> getUtentes() {
		return getInstance() == null ? null : new ArrayList<Utente>(
				getInstance().getUtentes());
	}

	public List<Feedback> getFeedbacksForValidatore() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForValidatore());
	}

}
