package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("feedbackHome")
public class FeedbackHome extends EntityHome<Feedback> {

	@In(create = true, value="accountHome")
	AccountHome accountHomeByValidatore;
	@In(create = true)
	OrdineHome ordineHome;
	@In(create = true, value="accountHome")
	AccountHome accountHomeBySegnalatore;
	@In(create = true, value="accountHome")
	AccountHome accountHomeByDestinatario;

	public void setFeedbackIdfeedback(Integer id) {
		setId(id);
	}

	public Integer getFeedbackIdfeedback() {
		return (Integer) getId();
	}

	@Override
	protected Feedback createInstance() {
		Feedback feedback = new Feedback();
		return feedback;
	}

	public void wire() {
		getInstance();
		Account accountByDestinatario = accountHomeByDestinatario.getDefinedInstance();
		if (accountByDestinatario != null) {
			getInstance().setAccountByDestinatario(accountByDestinatario);
		}
		Ordine ordine = ordineHome.getDefinedInstance();
		if (ordine != null) {
			getInstance().setOrdine(ordine);
		}
		Account accountByValidatore = accountHomeByValidatore.getDefinedInstance();
		if (accountByValidatore != null) {
			getInstance().setAccountByValidatore(accountByValidatore);
		}
		Account accountBySegnalatore = accountHomeBySegnalatore.getDefinedInstance();
		if (accountBySegnalatore != null) {
			getInstance().setAccountBySegnalatore(accountBySegnalatore);
		}
	}

	public boolean isWired() {
		if (getInstance().getAccountByDestinatario() == null)
			return false;
		if (getInstance().getOrdine() == null)
			return false;
		if (getInstance().getAccountByValidatore() == null)
			return false;
		if (getInstance().getAccountBySegnalatore() == null)
			return false;
		return true;
	}

	public Feedback getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
