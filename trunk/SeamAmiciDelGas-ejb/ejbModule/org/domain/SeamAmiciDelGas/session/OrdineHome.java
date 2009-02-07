package org.domain.SeamAmiciDelGas.session;

import org.domain.SeamAmiciDelGas.entity.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("ordineHome")
public class OrdineHome extends EntityHome<Ordine> {

	@In(create = true)
	AccountHome accountHome;
	@In(create = true)
	ArticoloHome articoloHome;

	public void setOrdineIdordine(Integer id) {
		setId(id);
	}

	public Integer getOrdineIdordine() {
		return (Integer) getId();
	}

	@Override
	protected Ordine createInstance() {
		Ordine ordine = new Ordine();
		return ordine;
	}

	public void wire() {
		getInstance();
		Account account = accountHome.getDefinedInstance();
		if (account != null) {
			getInstance().setAccount(account);
		}
		Articolo articolo = articoloHome.getDefinedInstance();
		if (articolo != null) {
			getInstance().setArticolo(articolo);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Ordine getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Feedback> getFeedbacks() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacks());
	}

	public List<Feedback> getFeedbacks_1() {
		return getInstance() == null ? null : new ArrayList<Feedback>(
				getInstance().getFeedbacks_1());
	}

}
