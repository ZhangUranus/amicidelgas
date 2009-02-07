package org.domain.SeamAmiciDelGas.session;

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
		if (getInstance().getUtente() == null)
			return false;
		return true;
	}

	public Account getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Itinerario> getItinerarios() {
		return getInstance() == null ? null : new ArrayList<Itinerario>(
				getInstance().getItinerarios());
	}

	public List<Questionario> getQuestionarios() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios());
	}

	public List<Feedback> getFeedbacksForSegnalatore() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForSegnalatore());
	}

	public List<Cybercontadino> getCybercontadinos() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos());
	}

	public List<Feedback> getFeedbacksForValidatore() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForValidatore());
	}

	public List<Feedback> getFeedbacksForSegnalatore_1() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForSegnalatore_1());
	}

	public List<Ordine> getOrdines() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines());
	}

	public List<Itinerario> getItinerarios_1() {
		return getInstance() == null ? null : new ArrayList<Itinerario>(
				getInstance().getItinerarios_1());
	}

	public List<Ruolo> getRuolos() {
		return getInstance() == null ? null : new ArrayList<Ruolo>(
				getInstance().getRuolos());
	}

	public List<Cybercontadino> getCybercontadinos_1() {
		return getInstance() == null ? null : new ArrayList<Cybercontadino>(
				getInstance().getCybercontadinos_1());
	}

	public List<Ruolo> getRuolos_1() {
		return getInstance() == null ? null : new ArrayList<Ruolo>(
				getInstance().getRuolos_1());
	}

	public List<Feedback> getFeedbacksForDestinatario() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForDestinatario());
	}

	public List<Feedback> getFeedbacksForDestinatario_1() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForDestinatario_1());
	}

	public List<Feedback> getFeedbacksForValidatore_1() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacksForValidatore_1());
	}

	public List<Ordine> getOrdines_1() {
		return getInstance() == null ? null : new ArrayList<Ordine>(
				getInstance().getOrdines_1());
	}

	public List<Questionario> getQuestionarios_1() {
		return getInstance() == null ? null : new ArrayList<Questionario>(
				getInstance().getQuestionarios_1());
	}

}
