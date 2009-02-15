package org.domain.SeamAmiciDelGas.crud;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("accountHome")
public class AccountHome extends EntityHome<Account> {

	@In(create = true)
	UtenteHome utenteHome;
	@In(create = true)
	PagamentoelettronicoHome pagamentoelettronicoHome;

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
		Utente utente = utenteHome.getDefinedInstance();
		if (utente != null) {
			getInstance().setUtente(utente);
		}
		Pagamentoelettronico pagamentoelettronico = pagamentoelettronicoHome
				.getDefinedInstance();
		if (pagamentoelettronico != null) {
			getInstance().setPagamentoelettronico(pagamentoelettronico);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Account getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Itinerario> getItinerarios() {
		return getInstance() == null ? null : new ArrayList<Itinerario>(
				getInstance().getItinerarios());
	}

	public List<Cybercontadino> getCybercontadinos() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos());
	}

	public List<Role> getRoles() {
		return getInstance() == null ? null : new ArrayList<Role>(
				getInstance().getRoles());
	}

	public List<Questionario> getQuestionarios() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios());
	}

	public List<Feedback> getFeedbacksForDestinatario() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForDestinatario());
	}

	public List<Feedback> getFeedbacksForSegnalatore() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForSegnalatore());
	}

	public List<Feedback> getFeedbacksForValidatore() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForValidatore());
	}

	public List<Ordine> getOrdines() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines());
	}

}
